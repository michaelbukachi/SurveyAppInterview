package org.mbukachi.domain

import kotlinx.coroutines.flow.Flow

interface SurveyRepo {

    fun getSurvey(lang: String = "en"): Flow<DataResult>

    fun saveResponse(response: Response): Flow<DataResult>

    suspend fun submitResponses()
}