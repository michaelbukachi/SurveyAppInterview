package org.mbukachi.survey_app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.testing.WorkManagerTestInitHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.declare
import org.mbukachi.domain.DataResult
import org.mbukachi.domain.Response
import org.mbukachi.domain.SurveyRepo

@RunWith(AndroidJUnit4::class)
class SubmitResponsesWorkerTest : KoinTest {
    @get:Rule
    val workManagerTestRule = WorkManagerTestRule()

    @Test
    fun testWorkerIsExecutedWhenThereIsNetworkConnection() {
        declare<SurveyRepo> {
            FakeSurveyRepo()
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequestBuilder<SubmitResponsesWorker>()
            .setConstraints(constraints)
            .build()

        val testDriver = WorkManagerTestInitHelper.getTestDriver(workManagerTestRule.targetContext)
        workManagerTestRule.workManager.enqueue(request).result.get()
        testDriver!!.setAllConstraintsMet(request.id)
        val workInfo = workManagerTestRule.workManager.getWorkInfoById(request.id).get()
        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
    }

}

class FakeSurveyRepo : SurveyRepo {
    override fun getSurvey(lang: String): Flow<DataResult> {
        return emptyFlow()
    }

    override suspend fun submitResponses() {
    }

    override fun saveResponse(response: Response): Flow<DataResult> {
        return emptyFlow()
    }

}