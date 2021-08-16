package org.mbukachi.data.network

import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SurveyApi {

    @GET("d628facc-ec18-431d-a8fc-9c096e00709a")
    suspend fun fetchSurvey(): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("dummy")
    suspend fun submitResponse(@Body payload: ResponsePayload): Response<Status>
}