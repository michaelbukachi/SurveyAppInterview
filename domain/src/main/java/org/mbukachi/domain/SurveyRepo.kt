package org.mbukachi.domain

import kotlinx.coroutines.flow.Flow

interface SurveyRepo {

    suspend fun getSurvey(lang: String = "en"): Flow<DataResult>
}