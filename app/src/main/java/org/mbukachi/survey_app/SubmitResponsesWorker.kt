package org.mbukachi.survey_app

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mbukachi.domain.SurveyRepo

class SubmitResponsesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val surveyRepo: SurveyRepo by inject()

    override suspend fun doWork(): Result {
        surveyRepo.submitResponses()
        return Result.success()
    }
}