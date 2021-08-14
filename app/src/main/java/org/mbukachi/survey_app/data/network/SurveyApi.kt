package com.chepsi.survey.app.data.network

import com.chepsi.survey.app.data.network.models.SurveyDTO
import com.chepsi.survey.app.domain.repos.SurveyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyApi {
    @GET("interview")
    suspend fun fetchSurvey(): Response<SurveyDTO>

    @POST("interview/survey-submit")
    suspend fun submitSurvey(
        @Body response: List<SurveyResponse>
    )
}