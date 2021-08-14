package org.mbukachi.data

import org.mbukachi.data.db.*
import org.mbukachi.domain.*
import org.mbukachi.domain.AnswerType
import org.mbukachi.domain.QuestionType

fun OptionEntity.toOption(lang: String, strings: List<StringEntity>) = Option(
    key = strings.filter { it.lang == lang && it.key == displayText }.map { it.value }
        .first(),
    value = value
)

fun QuestionEntity.toQuestion(lang: String, strings: List<StringEntity>) = Question(
    id = id,
    questionType = QuestionType.valueOf(questionType.name),
    answerType = AnswerType.valueOf(answerType.name),
    questionText = strings.filter { it.lang == lang && it.key == questionText }.map { it.value }
        .first(),
    next = next
)

fun QuestionEntityWithOptions.toQuestion(lang: String, strings: List<StringEntity>) =
    question.toQuestion(lang, strings).copy(options = options.map { it.toOption(lang, strings) })

fun SurveyEntityWithQuestions.toSurvey(lang: String): Survey {
    val startQuestion = questions.filter {
        it.question.id == survey.startQuestionId
    }.map {
        it.toQuestion(lang, strings)
    }.first()

    return Survey(
        id = survey.id,
        startQuestion = startQuestion,
        questions = questions.map { it.toQuestion(lang, strings) }
    )
}