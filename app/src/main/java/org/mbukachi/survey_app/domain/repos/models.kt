package com.chepsi.survey.app.domain.repos

data class Survey(
    val id: String = "",
    val questions: List<Question> = listOf(),
    val startQuestionId: String = ""
)

data class Question(
    val id: String = "",
    val answerType: String = "",
    val next: String = "",
    val options: List<Option> = listOf(),
    val questionText: String,
    val questionType: String
)

data class Option(
    val displayText: String = "",
    val value: String = ""
)

data class SurveyResponse(
    val id: String = "",
    val answers: List<Answer> = listOf()
)

data class Answer(
    val question: String = "",
    val answer: String = ""
)

enum class QuestionType {
    FREE_TEXT, SELECT_ONE
}

enum class AnswerType {
    SINGLE_LINE_TEXT, INTEGER
}