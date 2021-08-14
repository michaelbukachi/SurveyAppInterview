package com.chepsi.survey.app.domain.usecases

import com.chepsi.survey.app.domain.repos.SurveyRepo
import com.chepsi.survey.app.domain.repos.SurveyResponse

typealias SubmitSurveyBaseUseCase = BaseUseCase<SurveyResponse, Unit>

class SubmitSurveyUseCase(private val surveyRepo: SurveyRepo) : SubmitSurveyBaseUseCase {
    override suspend fun invoke(params: SurveyResponse) =
        surveyRepo.submitSurvey(params)
}