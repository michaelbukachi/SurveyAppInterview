package org.mbukachi.survey_app.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.chepsi.survey.app.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.mbukachi.survey_app.SubmitResponsesWorker
import org.mbukachi.survey_app.ui.theme.SurveyAppTheme
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
//        setContent {
//            SurveyAppTheme {
//                Surface(color = MaterialTheme.colors.background) {
//                    MainScreen()
//                }
//            }
//        }
        createSubmitSurveyWorker()
    }


    private fun attachObservers() {
//        mainViewModel.survey.observe(this, { fetchedSurvey ->
//            fetchedSurvey?.let {
//                setupViewPager(it)
//            }
//        })
//
//        mainViewModel.currentPosition.observe(this, {
//            binding.surveyVP.currentItem = it
//        })
    }

    private fun setupViewPager() {

//        val surveyAdapter = SurveyQuestionsPagerAdapter(this, survey)
//        binding.surveyVP.apply {
//            adapter = surveyAdapter
//            isUserInputEnabled = false
//        }
    }

    private fun createSubmitSurveyWorker() = lifecycleScope.launch {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workerRequest = PeriodicWorkRequestBuilder<SubmitResponsesWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(SubmitResponsesWorker::class.simpleName!!)
            .build()

        val workManager = WorkManager.getInstance(this@MainActivity)
        val infoList =
            workManager.getWorkInfosByTag(SubmitResponsesWorker::class.simpleName!!).await()
        if (infoList.isEmpty()) {
            workManager.enqueueUniquePeriodicWork(
                SubmitResponsesWorker::class.simpleName!!,
                ExistingPeriodicWorkPolicy.KEEP,
                workerRequest
            )
        }
    }

    fun swipeNext() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
//        binding.surveyVP.currentItem = binding.surveyVP.currentItem +
    }

    fun viewPagerReset() {
//        Snackbar.make(binding.root, "Data Saved Successfully", Snackbar.LENGTH_LONG).show()
//        mainViewModel.currentPosition.postValue(0)
//        mainViewModel.resetResponse()
    }
}

@ExperimentalPagerApi
@Composable
fun MainScreen() {
    val mainViewModel = getViewModel<MainViewModel>()
    //val uiState: UIState by mainViewModel.surveyState.collectAsState(initial = UIState.Loading)
    //val pagerState = rememberPagerState(pageCount = 10)

//    SwipeRefresh(
//        state = rememberSwipeRefreshState(uiState is UIState.Loading),
//        onRefresh = {}
//    ) {
//        Column(
//            modifier = Modifier.verticalScroll(rememberScrollState())
//        ) {
//            if (uiState is UIState.SurveyLoaded) {
//                HorizontalPager(state = pagerState) {
//
//                }
//            }
//
//            if (uiState is UIState.Error) {
//                Text((uiState as UIState.Error).message)
//            }
//        }
//    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SurveyAppTheme {
//        MainScreen()
    }
}