package org.mbukachi.data.db

import androidx.room.*

@Entity(tableName = "surveys")
data class SurveyEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "start_question_id") val startQuestionId: String,
)

data class SurveyEntityWithQuestions(
    @Embedded val survey: SurveyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "surveyId"
    )
    val strings: List<StringEntity> = emptyList(),
    @Relation(
        entity = QuestionEntity::class,
        parentColumn = "id",
        entityColumn = "surveyId"
    )
    val questions: List<QuestionEntityWithOptions> = emptyList()
)

@Entity
data class QuestionEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "question_type") val questionType: QuestionType,
    @ColumnInfo(name = "answer_type") val answerType: AnswerType,
    @ColumnInfo(name = "question_text") val questionText: String,
    val next: String?,
    val surveyId: String
)

data class QuestionEntityWithOptions(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val options: List<OptionEntity> = emptyList(),
)

@Entity
data class OptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val value: String,
    val questionId: String,
    @ColumnInfo(name = "display_text") val displayText: String
)

@Entity
data class StringEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lang: String,
    val key: String,
    val value: String,
    val surveyId: String
)