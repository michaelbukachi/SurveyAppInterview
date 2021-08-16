package org.mbukachi.survey_app.ui.survey

data class Response(
    val id: String
)

data class Survey(
    val id: String,
    val questions: List<Question>
)

data class Question(
    val id: Int,
    val questionText: String,
    val answer: PossibleAnswer
)

data class Option(
    val label: String,
    val value: String
)


sealed class PossibleAnswer {
    data class SingleChoice(val options: List<Option>) : PossibleAnswer()
    data class InputChoice(val input: String) : PossibleAnswer()
}

sealed class Answer<T : PossibleAnswer> {
    data class SingleChoice(val answer: String) : Answer<PossibleAnswer.SingleChoice>()
    data class Input(val answer: String) : Answer<PossibleAnswer.InputChoice>()
}
