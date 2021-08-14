package com.chepsi.survey.app.data.network.models

import com.google.gson.annotations.SerializedName

data class OptionDTO(
    @SerializedName("display_text")
    val displayText: String = "",
    @SerializedName("value")
    val value: String = ""
)