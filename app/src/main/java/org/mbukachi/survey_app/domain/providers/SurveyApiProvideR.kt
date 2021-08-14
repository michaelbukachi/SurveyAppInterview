package com.chepsi.survey.app.domain.providers

import com.chepsi.survey.app.data.network.SurveyApi
import retrofit2.Retrofit

fun provideSurveyApi(retrofit: Retrofit) =
    retrofit.create(SurveyApi::class.java)