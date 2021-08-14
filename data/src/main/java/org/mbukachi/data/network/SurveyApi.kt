package org.mbukachi.data.network

import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface SurveyApi {

    @GET("/")
    suspend fun fetchSurvey(): Response<JsonObject>
}