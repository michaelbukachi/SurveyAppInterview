package org.mbukachi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [SurveyEntity::class, QuestionEntity::class, OptionEntity::class, StringEntity::class, ResponseEntity::class, AnswerEntity::class],
    version = 1
)
@TypeConverters(QuestionTypeConverter::class, AnswerTypeConverter::class)
abstract class SurveyAppDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
}