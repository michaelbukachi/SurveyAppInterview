package org.mbukachi.survey_app

import org.koin.dsl.module
import org.mbukachi.survey_app.ui.MainViewModel

val appModule = module {
    single { MainViewModel(get()) }
}