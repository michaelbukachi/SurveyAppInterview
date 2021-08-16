package org.mbukachi.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePayload(
    @SerialName("id") val id: Long,
    @SerialName("survey_id") val surveyId: String,
    val answers: List<AnswerPayload>
)

@Serializable
data class AnswerPayload(
    @SerialName("question_id") val questionId: String,
    val answer: String
)

@Serializable
data class Status(
    val status: String
)