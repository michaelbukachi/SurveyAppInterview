package org.mbukachi.data

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.mbukachi.data.db.SurveyAppDatabase
import org.mbukachi.data.network.SurveyApi
import org.mbukachi.domain.SurveyRepo
import retrofit2.Retrofit

val dataModules = module {
    single {
        Room.databaseBuilder(
            get(),
            SurveyAppDatabase::class.java, "survey_app"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<SurveyAppDatabase>().surveyDao()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        "https://run.mocky.io/v3/d628facc-ec18-431d-a8fc-9c096e00709a".toHttpUrl()
    }

    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(get<HttpUrl>())
            .client(get())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    single {
        get<Retrofit>().create(SurveyApi::class.java)
    }

    single<SurveyRepo> {
        SurveyRepoImpl(get(), get())
    }
}