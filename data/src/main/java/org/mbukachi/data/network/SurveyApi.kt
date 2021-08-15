package org.mbukachi.data.network

import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface SurveyApi {

    @GET("d628facc-ec18-431d-a8fc-9c096e00709a")
    suspend fun fetchSurvey(): Response<JsonObject>
}