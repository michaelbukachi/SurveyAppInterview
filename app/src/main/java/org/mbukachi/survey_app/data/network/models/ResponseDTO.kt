package com.chepsi.survey.app.data.network.models

import com.chepsi.survey.app.domain.repos.Answer
import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("answer")
    val answers: ArrayList<Answer> = ArrayList()
)
