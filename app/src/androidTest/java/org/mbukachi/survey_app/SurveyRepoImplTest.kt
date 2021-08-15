package com.chepsi.survey.app

import androidx.test.annotation.UiThreadTest
import androidx.test.platform.app.InstrumentationRegistry
import com.chepsi.survey.app.data.network.models.SurveyDTO
import com.chepsi.survey.app.data.repos.SurveyRepoImpl
import com.chepsi.survey.app.domain.di.dbModule
import com.chepsi.survey.app.domain.di.domainModule
import com.chepsi.survey.app.domain.repos.Survey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.realm.Realm
import io.realm.RealmConfiguration
import junit.framework.Assert.assertNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declare
import retrofit2.Response
import kotlin.time.ExperimentalTime


@ExperimentalTime
class SurveyRepoImplTest : KoinTest {

    private val db: Realm by inject()
    private val api: SurveyApi by inject()


    @Before
    fun setup() {
        loadKoinModules(listOf(domainModule, dbModule))
        declare {
            Realm.init(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            RealmConfiguration.Builder()
                .inMemory()
                .allowWritesOnUiThread(true)
                .build()
        }
        declare {
            mockk<SurveyApi>()
        }
    }

    @After
    @UiThreadTest
    fun tearDown() {
        db.executeTransaction {
            db.deleteAll()
        }
        unloadKoinModules(listOf(domainModule, dbModule))
    }

    @Test
    @UiThreadTest
    fun checkNetworkCallIsMadeWhenDbIsEmpty() = runBlocking {
        val surveyDTO = SurveyDTO(
            id = "test"
        )
        coEvery { api.fetchSurvey() } returns Response.success(200, surveyDTO)
        val repo = SurveyRepoImpl(db, api)
        repo.fetchSurvey().test {
            expectItem()
            coVerify { api.fetchSurvey() }
        }
    }

    @Test
    @UiThreadTest
    fun checkNetworkCallIsNotMadeWhenDbIsNotEmpty() = runBlocking {
        val repo = SurveyRepoImpl(db, api)
        repo.addSurvey(Survey(id = "test"))
        delay(1000)
        repo.fetchSurvey().test {
            assertNull(expectItem())
            coVerify(inverse = true) { api.fetchSurvey() }
        }
    }
}