package org.mbukachi.survey_app

import org.koin.dsl.module
import org.mbukachi.survey_app.ui.MainViewModel
import org.mbukachi.survey_app.ui.survey.SurveyRepository
import org.mbukachi.survey_app.ui.survey.SurveyViewModel

val appModule = module {
    single { MainViewModel(get()) }
    single { SurveyRepository }
    single { SurveyViewModel(get()) }
}