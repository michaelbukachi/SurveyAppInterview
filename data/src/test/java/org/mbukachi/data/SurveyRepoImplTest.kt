package org.mbukachi.data

import androidx.room.Room
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mbukachi.data.db.SurveyAppDatabase
import org.mbukachi.data.db.SurveyDao
import org.mbukachi.data.network.SurveyApi
import org.mbukachi.domain.DataResult
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, sdk = [30])
class SurveyRepoImplTest : KoinTest {

    val surveyApi by inject<SurveyApi>()
    val surveyDao by inject<SurveyDao>()

    val testAppModule = module {
        single {
            RuntimeEnvironment.getApplication().applicationContext
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(dataModules)
        modules(testAppModule)
    }

    lateinit var surveyRepo: SurveyRepoImpl
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        ShadowLog.stream = System.out
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = TestResponseDispatcher()
        mockWebServer.start()
        declare {
            mockWebServer.url("/")
        }
        declare {
            Room.inMemoryDatabaseBuilder(
                get(), SurveyAppDatabase::class.java
            ).build()
        }
        surveyRepo = SurveyRepoImpl(surveyApi, surveyDao)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `survey is fetched saved successfully`() {
        runBlocking {
            val surveyFlow = surveyRepo.getSurvey()
            val result = surveyFlow.first()
            assertThat(result, allOf(instanceOf(DataResult.Success::class.java)))
        }
    }
}