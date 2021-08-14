package org.mbukachi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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
}