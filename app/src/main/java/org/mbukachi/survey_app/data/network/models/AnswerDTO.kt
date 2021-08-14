package com.chepsi.survey.app.data.network.models

import com.google.gson.annotations.SerializedName

data class AnswerDTO(
    @SerializedName("question")
    val question: String = "",
    @SerializedName("answer")
    val answer: String = ""
)