package org.mbukachi.survey_app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import org.mbukachi.domain.DataResult
import org.mbukachi.domain.SurveyRepo


class MainViewModel(
    private val surveyRepo: SurveyRepo
) : ViewModel() {
    val surveyState by lazy {
        surveyRepo.getSurvey().map {
            when (it) {
                is DataResult.Success -> {
                    UIState.SurveyLoaded(it.survey)
                }
                is DataResult.Error -> {
                    UIState.Error(it.message)
                }
                DataResult.Loading -> {
                    UIState.Loading
                }
            }
        }
    }
}