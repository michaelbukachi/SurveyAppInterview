package org.mbukachi.data

import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.mbukachi.data.db.*
import org.mbukachi.data.network.SurveyApi
import org.mbukachi.domain.DataResult
import org.mbukachi.domain.SurveyRepo


class SurveyRepoImpl(private val api: SurveyApi, private val surveyDao: SurveyDao) : SurveyRepo {

    override fun getSurvey(lang: String): Flow<DataResult> = flow {
        if (!surveyDao.exists()) {
            val response = api.fetchSurvey()
            if (response.isSuccessful) {
                insertIntoDb(response.body()!!)
            } else {
                emit(DataResult.Error("Failed to fetch survey"))
                return@flow
            }
        }

        emitAll(surveyDao.getSurveys().map {
            DataResult.Success(it.first().toSurvey(lang))
        })
    }

    private suspend fun insertIntoDb(response: JsonObject) {
        surveyDao.insertSurvey(
            SurveyEntity(
                id = response.content("id"),
                startQuestionId = response.content("start_question_id")
            )
        )
        val strings = response["strings"]!!.jsonObject.entries.flatMap { entry ->
            entry.value.jsonObject.entries.map {
                StringEntity(
                    lang = entry.key,
                    key = it.key,
                    value = it.value.jsonPrimitive.content,
                    surveyId = response.content("id")
                )
            }

        }
        surveyDao.insertStrings(strings = strings.toTypedArray())

        val questions = response["questions"]!!.jsonArray.map {
            QuestionEntity(
                id = it.jsonObject.content("id"),
                questionType = QuestionType.valueOf(it.jsonObject.content("question_type")),
                answerType = AnswerType.valueOf(it.jsonObject.content("answer_type")),
                questionText = it.jsonObject.content("question_text"),
                next = it.jsonObject.contentNullable("next"),
                surveyId = response.content("id")
            )
        }
        surveyDao.insertQuestions(questions = questions.toTypedArray())

        val options = response["questions"]!!.jsonArray.flatMap { question ->
            question.jsonObject["options"]!!.jsonArray.map {
                OptionEntity(
                    value = it.jsonObject.content("value"),
                    displayText = it.jsonObject.content("display_text"),
                    questionId = question.jsonObject.content("id")
                )
            }
        }
        surveyDao.insertOptions(options = options.toTypedArray())
    }

    override suspend fun submitResponses() {
        val responses = surveyDao.getResponsesNotSubmitted()
        val successfulSubmissions = mutableListOf<ResponseEntity>()
        responses.forEach {
            val response = api.submitResponse(it.toPayload())
            if (response.isSuccessful) {
                successfulSubmissions.add(it.response.copy(submitted = true))
            } else {
                println("Failed to submit Survey Response ${it.response.id}")
            }
        }

        if (successfulSubmissions.isNotEmpty()) {
            surveyDao.updateResponses(responses = successfulSubmissions.toTypedArray())
        }
    }

}