package com.chepsi.survey.app.domain.usecases

import com.chepsi.survey.app.domain.repos.SurveyRepo
import com.chepsi.survey.app.domain.repos.SurveyResponse
import kotlinx.coroutines.flow.Flow

typealias AnsweredSurveyBaseUseCase = BaseUseCase<Unit, Flow<List<SurveyResponse>>>

class AnsweredSurveysUseCase(private val surveyRepo: SurveyRepo) : AnsweredSurveyBaseUseCase {
    override suspend fun invoke(params: Unit): Flow<List<SurveyResponse>> =
        surveyRepo.fetchResponses()
}