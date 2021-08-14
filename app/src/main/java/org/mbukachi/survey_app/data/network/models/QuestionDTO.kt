package com.chepsi.survey.app.data.network.models

import com.google.gson.annotations.SerializedName

data class QuestionDTO(
    @SerializedName("answer_type")
    val answerType: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("next")
    val next: String = "",
    @SerializedName("options")
    val options: List<OptionDTO> = emptyList(),
    @SerializedName("question_text")
    val questionText: String = "",
    @SerializedName("question_type")
    val questionType: String = ""
)