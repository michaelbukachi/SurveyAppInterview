package com.chepsi.survey.app

import com.chepsi.survey.app.domain.repos.SurveyResponse

val testSurveyResponse = SurveyResponse(
    id = "1",
    answers = emptyList()
)

val testResponses = listOf(
    testSurveyResponse,
    testSurveyResponse
)