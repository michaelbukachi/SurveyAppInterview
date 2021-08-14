package com.chepsi.survey.app.data.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.chepsi.survey.app.domain.repos.SurveyResponse
import com.chepsi.survey.app.domain.usecases.SendSurveyUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class SubmitSurveyWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
    private val sendSurveyUseCase: SendSurveyUseCase by inject()


    override suspend fun doWork(): Result {
        createForegroundInfo()
        val gson = Gson()
        val surveyType = object : TypeToken<List<SurveyResponse>>() {}.type
        val surveyInputString = inputData.getString("survey")
        val surveyInput: List<SurveyResponse> = gson.fromJson(surveyInputString, surveyType)
        Timber.d(inputData.getString("survey")!!)
        sendSurveyUseCase.invoke(surveyInput)
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        val notification = NotificationCompat.Builder(applicationContext, "surveySubmission")
            .setContentTitle("Submitting Survey.....")
            .setTicker("Submitting survey")
            .setSmallIcon(R.drawable.notification_action_background)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, "Cancel Submission", intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notification, "surveySubmission")
        }

        return ForegroundInfo(1, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationBuilder: NotificationCompat.Builder, id: String) {
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
        val channel = NotificationChannel(
            id,
            "SurveyApp",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "SurveyApp Notifications"
        notificationManager.createNotificationChannel(channel)
    }
}