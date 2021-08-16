package org.mbukachi.survey_app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.mbukachi.data.dataModules
import timber.log.Timber

class SurveyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()

        plantTrees()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@SurveyApp)
            modules(dataModules)
            modules(appModule)
        }
    }

    private fun plantTrees() {
        Timber.plant(Timber.DebugTree())
    }
}