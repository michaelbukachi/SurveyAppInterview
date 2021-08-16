package org.mbukachi.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyDao {
    @Insert
    suspend fun insertSurvey(surveyEntity: SurveyEntity)

    @Insert
    suspend fun insertStrings(vararg strings: StringEntity)

    @Insert
    suspend fun insertQuestions(vararg questions: QuestionEntity)

    @Insert
    suspend fun insertOptions(vararg options: OptionEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM surveys)")
    suspend fun exists(): Boolean

    @Transaction
    @Query("SELECT * FROM surveys")
    fun getSurveys(): Flow<List<SurveyEntityWithQuestions>>

    @Transaction
    @Query("SELECT * FROM responses WHERE submitted = 0")
    suspend fun getResponsesNotSubmitted(): List<ResponseEntityWithAnswers>

    @Update
    suspend fun updateResponses(vararg responses: ResponseEntity)
}