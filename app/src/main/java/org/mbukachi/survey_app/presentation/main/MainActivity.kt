package com.chepsi.survey.app.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chepsi.survey.app.R
import com.chepsi.survey.app.data.workers.SubmitSurveyWorker
import com.chepsi.survey.app.databinding.ActivityMainBinding
import com.chepsi.survey.app.domain.repos.Survey
import com.chepsi.survey.app.domain.repos.SurveyResponse
import com.chepsi.survey.app.presentation.pager.SurveyQuestionsPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModel()
    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.fetchSurvey()

        attachObservers()

        fetchSavedResponses()

    }

    private fun fetchSavedResponses() {
        mainViewModel.saveSurveyResponses.observe(this) {
            if (it.isNotEmpty()) {
                createSubmitSurveyWorker(it)
            }
        }
    }

    private fun attachObservers() {
        mainViewModel.survey.observe(this, { fetchedSurvey ->
            fetchedSurvey?.let {
                setupViewPager(it)
            }
        })

        mainViewModel.currentPosition.observe(this, {
            binding.surveyVP.currentItem = it
        })
    }

    private fun setupViewPager(survey: Survey) {

        val surveyAdapter = SurveyQuestionsPagerAdapter(this, survey)
        binding.surveyVP.apply {
            adapter = surveyAdapter
            isUserInputEnabled = false
        }
    }

    private fun createSubmitSurveyWorker(answeredSurveys: List<SurveyResponse>) {
        val imageWorker = PeriodicWorkRequestBuilder<SubmitSurveyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag("submitSurveyWork")
            //.setInputData(workDataOf("survey" to answeredSurveys.toString()))
            .setInputData(workDataOf("survey" to Gson().toJson(answeredSurveys)))
            .build()
        workManager.enqueueUniquePeriodicWork(
            "periodicSubmitSurvey",
            ExistingPeriodicWorkPolicy.KEEP,
            imageWorker
        )

    }

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun swipeNext() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        binding.surveyVP.currentItem = binding.surveyVP.currentItem + 1
    }

    fun viewPagerReset() {
        Snackbar.make(binding.root, "Data Saved Successfully", Snackbar.LENGTH_LONG).show()
        mainViewModel.currentPosition.postValue(0)
        mainViewModel.resetResponse()
    }
}