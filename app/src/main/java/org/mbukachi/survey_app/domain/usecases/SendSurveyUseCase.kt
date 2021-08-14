package com.chepsi.survey.app.domain.usecases

import com.chepsi.survey.app.domain.repos.SurveyRepo
import com.chepsi.survey.app.domain.repos.SurveyResponse

typealias SendSurveyBaseUseCase = BaseUseCase<List<SurveyResponse>, Unit>

class SendSurveyUseCase(private val surveyRepo: SurveyRepo) : SendSurveyBaseUseCase {
    override suspend fun invoke(params: List<SurveyResponse>) =
        surveyRepo.sendSurvey(params)
}