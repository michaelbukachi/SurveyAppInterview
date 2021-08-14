package com.chepsi.survey.app.domain.usecases

import com.chepsi.survey.app.domain.repos.Survey
import com.chepsi.survey.app.domain.repos.SurveyRepo
import kotlinx.coroutines.flow.Flow

typealias SurveyBaseUseCase = BaseUseCase<Unit, Flow<Survey?>>


class SurveyUseCase(private val surveyRepo: SurveyRepo) : SurveyBaseUseCase {
    override suspend fun invoke(params: Unit): Flow<Survey?> =
        surveyRepo.fetchSurvey()
}