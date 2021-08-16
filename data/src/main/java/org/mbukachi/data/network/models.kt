package org.mbukachi.data.network

import kotlinx.serialization.SerialName

data class ResponsePayload(
    @SerialName("id") val id: Long,
    @SerialName("survey_id") val surveyId: String,
    val answers: List<AnswerPayload>
)

data class AnswerPayload(
    @SerialName("question_id") val questionId: String,
    val answer: String
)

data class Status(
    val status: String
)