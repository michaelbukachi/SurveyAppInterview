package org.mbukachi.domain

import kotlinx.coroutines.flow.Flow

interface SurveyRepo {

    fun getSurvey(lang: String = "en"): Flow<DataResult>

    suspend fun saveResponse(response: Response)

    suspend fun submitResponses()
}