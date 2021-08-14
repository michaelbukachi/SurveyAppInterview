package com.chepsi.survey.app.data.network.models

import com.google.gson.annotations.SerializedName

data class SurveyDTO(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("questions")
    val questions: List<QuestionDTO> = emptyList(),
    @SerializedName("start_question_id")
    val startQuestionId: String = "1"
)