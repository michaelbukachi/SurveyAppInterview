package org.mbukachi.data.db

import androidx.room.TypeConverter

enum class QuestionType {
    FREE_TEXT,
    SELECT_ONE,
    TYPE_VALUE
}

enum class AnswerType {
    SINGLE_LINE_TEXT,
    FLOAT
}

class QuestionTypeConverter {
    @TypeConverter
    fun fromQuestionType(questionType: QuestionType): String {
        return questionType.name
    }

    @TypeConverter
    fun toQuestionType(questionType: String): QuestionType {
        return QuestionType.valueOf(questionType)
    }
}

class AnswerTypeConverter {
    @TypeConverter
    fun fromAnswerType(answerType: AnswerType): String {
        return answerType.name
    }

    @TypeConverter
    fun toAnswerType(answerType: String): AnswerType {
        return AnswerType.valueOf(answerType)
    }
}