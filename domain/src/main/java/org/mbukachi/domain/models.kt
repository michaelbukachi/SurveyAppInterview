package org.mbukachi.domain

enum class QuestionType {
    FREE_TEXT,
    SELECT_ONE,
    TYPE_VALUE
}

enum class AnswerType {
    SINGLE_LINE_TEXT,
    FLOAT
}

data class Option(
    val key: String,
    val value: String
)

data class Question(
    val id: String,
    val questionType: QuestionType,
    val answerType: AnswerType,
    val questionText: String,
    val options: List<Option> = emptyList(),
    val next: String?
)

data class Survey(
    val id: String,
    val startQuestion: Question,
    val questions: List<Question> = emptyList()
)

sealed class DataResult {
    data class Success(val survey: Survey) : DataResult()
    data class Error(val message: String) : DataResult()
    object Loading : DataResult()
}