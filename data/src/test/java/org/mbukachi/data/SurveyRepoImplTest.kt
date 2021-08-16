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
import org.mbukachi.domain.Answer
import org.mbukachi.domain.DataResult
import org.mbukachi.domain.Response
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertNotEquals

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, sdk = [30])
class SurveyRepoImplTest : KoinTest {

    val surveyApi by inject<SurveyApi>()
    val surveyDao by inject<SurveyDao>()
    val db by inject<SurveyAppDatabase>()

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
        db.close()
        mockWebServer.shutdown()
    }

    @Test
    fun `survey is fetched saved successfully`() {
        mockWebServer.enqueueResponse("response.json", HTTP_OK)
        runBlocking {
            val surveyFlow = surveyRepo.getSurvey()
            val result = surveyFlow.first()
            assertThat(result, allOf(instanceOf(DataResult.Success::class.java)))
        }
    }

    @Test
    fun `save response in db`() {
        runBlocking {
            val response = Response(
                surveyId = "test",
                answers = listOf(
                    Answer(questionId = "test", value = "test")
                )
            )
            val resultFlow = surveyRepo.saveResponse(response)
            val result = resultFlow.first()
            assertThat(result, allOf(instanceOf(DataResult.ResponseSaved::class.java)))
        }
    }

    @Test
    fun `submit response`() = runBlocking {
        mockWebServer.enqueueResponse("submission_response.json", HTTP_OK)
        surveyRepo.getSurvey()
        val response = Response(
            surveyId = "farmer_survey",
            answers = listOf(
                Answer(questionId = "q_farmer_gender", value = "test"),
                Answer(questionId = "q_farmer_gender", value = "test"),
                Answer(questionId = "q_size_of_farm", value = "test")
            )
        )
        surveyRepo.saveResponse(response).first()
        val responseNotSubmitted = surveyDao.getResponsesNotSubmitted()
        surveyRepo.submitResponses()
        assertNotEquals(responseNotSubmitted.size, surveyDao.getResponsesNotSubmitted().size)
    }
}