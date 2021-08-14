package com.chepsi.survey.app.domain.repos

import kotlinx.coroutines.flow.Flow

interface SurveyRepo {
    suspend fun addSurvey(survey: Survey)

    suspend fun fetchSurvey(): Flow<Survey?>

    suspend fun submitSurvey(response: SurveyResponse)

    suspend fun fetchResponses(): Flow<List<SurveyResponse>>

    suspend fun sendSurvey(response: List<SurveyResponse>)

}