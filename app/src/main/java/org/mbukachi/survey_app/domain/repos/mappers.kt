package com.chepsi.survey.app.domain.repos

import com.chepsi.survey.app.data.network.models.OptionDTO
import com.chepsi.survey.app.data.network.models.QuestionDTO
import com.chepsi.survey.app.data.network.models.SurveyDTO

fun SurveyDTO.toSurvey(): Survey = Survey(
    id = id ?: "",
    questions = questions?.map { it.toQuestion() } ?: emptyList(),
    startQuestionId = startQuestionId ?: ""
)

fun QuestionDTO.toQuestion(): Question = Question(
    id = id ?: "",
    answerType = answerType ?: "",
    next = next ?: "",
    options = options.map { it.toOption() },
    questionText = questionText ?: "",
    questionType = questionType ?: ""
)


fun OptionDTO.toOption(): Option = Option(
    displayText = displayText ?: "",
    value = value ?: ""
)