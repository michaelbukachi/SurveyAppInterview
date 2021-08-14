package com.chepsi.survey.app.domain.di

import com.chepsi.survey.app.data.db.isRealmInitialized
import com.chepsi.survey.app.data.repos.SurveyRepoImpl
import com.chepsi.survey.app.domain.providers.provideRetrofit
import com.chepsi.survey.app.domain.providers.provideSurveyApi
import com.chepsi.survey.app.domain.repos.SurveyRepo
import com.chepsi.survey.app.domain.usecases.AnsweredSurveysUseCase
import com.chepsi.survey.app.domain.usecases.SendSurveyUseCase
import com.chepsi.survey.app.domain.usecases.SubmitSurveyUseCase
import com.chepsi.survey.app.domain.usecases.SurveyUseCase
import com.chepsi.survey.app.presentation.main.MainViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.dsl.module
import timber.log.Timber

val appModule = module(true) {

}

val networkModule = module {
    factory { provideRetrofit(baseUrl = "https://6049ea8cfb5dcc001796acdc.mockapi.io/api/v1/") }
    factory { provideSurveyApi(get()) }
}

val dataModule = module {

}

val domainModule = module {
    single { SurveyUseCase(get()) }
    single { AnsweredSurveysUseCase(get()) }
    single { SendSurveyUseCase(get()) }
    single { SubmitSurveyUseCase(get()) }
}

val presentationModule = module {
    single { MainViewModel(get(), get(), get()) }
}

val dbModule = module {
    single {
        if (isRealmInitialized()) {
            Timber.i("Realm already initialized")
        } else {
            Realm.init(get())
        }
        RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
    }
    factory { Realm.getInstance(get()) }

    factory<SurveyRepo> { SurveyRepoImpl(get(), get()) }
}

val myKoinModules = listOf(
    appModule,
    dataModule,
    networkModule,
    dbModule,
    domainModule,
    presentationModule
)