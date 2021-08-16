package org.mbukachi.survey_app.ui.survey

data class Response(
    val id: String
)

data class SurveyUI(
    val id: String,
    val questions: List<QuestionUI>
)

data class QuestionUI(
    val id: String,
    val questionText: String,
    val answer: PossibleAnswer
)

data class OptionUI(
    val label: String,
    val value: String
)


sealed class PossibleAnswer {
    data class SingleChoice(val options: List<OptionUI>) : PossibleAnswer()
    data class InputChoice(val input: String) : PossibleAnswer()
    data class NumberInputChoice(val input: Float) : PossibleAnswer()
}

sealed class AnswerUI<T : PossibleAnswer> {
    data class SingleChoice(val answer: String) : AnswerUI<PossibleAnswer.SingleChoice>()
    data class Input(val answer: String) : AnswerUI<PossibleAnswer.InputChoice>()
    data class NumberInput(val answer: Float) : AnswerUI<PossibleAnswer.NumberInputChoice>()
}
