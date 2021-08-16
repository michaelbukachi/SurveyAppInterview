package org.mbukachi.survey_app

import org.koin.dsl.module
import org.mbukachi.survey_app.ui.survey.SurveyViewModel

val appModule = module {
    single { SurveyViewModel(get()) }
}