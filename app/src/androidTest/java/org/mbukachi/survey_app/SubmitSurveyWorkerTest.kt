package com.chepsi.survey.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.chepsi.survey.app.data.workers.SubmitSurveyWorker
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SubmitSurveyWorkerTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var workerManagerTestRule = WorkManagerTestRule()

    @Test
    fun testSubmitSurveyWorker() {
        // Create Work Request
        val work =
            TestListenableWorkerBuilder<SubmitSurveyWorker>(workerManagerTestRule.targetContext)
                .setInputData(workDataOf("survey" to Gson().toJson(testResponses)))
                .build()

        runBlocking {
            val result = work.doWork()
            // Assert
            Assert.assertNotNull(result)
        }
    }
}