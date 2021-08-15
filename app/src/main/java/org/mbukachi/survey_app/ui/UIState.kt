package org.mbukachi.survey_app.ui

import org.mbukachi.domain.Survey

sealed class UIState {
    object Loading : UIState()

    data class SurveyLoaded(val data: Survey) : UIState()

    data class Error(val message: String) : UIState()
}

