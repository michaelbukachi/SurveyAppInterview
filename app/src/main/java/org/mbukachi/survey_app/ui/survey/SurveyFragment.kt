package org.mbukachi.survey_app.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mbukachi.survey_app.ui.survey.composables.LoadingScreen
import org.mbukachi.survey_app.ui.survey.composables.SurveyDoneScreen
import org.mbukachi.survey_app.ui.survey.composables.SurveyQuestionsScreen
import org.mbukachi.survey_app.ui.theme.SurveyAppTheme

class SurveyFragment : Fragment() {

    private val viewModel: SurveyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            viewModel.fetchSurveys()

            setContent {
                SurveyAppTheme {
                    val surveyState = viewModel.uiState.collectAsState()
                    when (surveyState.value) {
                        is SurveyState.Questions -> SurveyQuestionsScreen(
                            questions = surveyState.value as SurveyState.Questions,
                            onDonePressed = { viewModel.submitResponse(surveyState.value as SurveyState.Questions) },
                            onBackPressed = {
                                viewModel.resetState()
                                activity?.onBackPressedDispatcher?.onBackPressed()
                            },
                        )
                        is SurveyState.Done -> SurveyDoneScreen(
                            onDonePressed = {
                                viewModel.resetState()
                                activity?.onBackPressedDispatcher?.onBackPressed()
                            }
                        )
                        SurveyState.Loading -> LoadingScreen()
                        SurveyState.RestartSurvey -> {
                            viewModel.fetchSurveys()
                        }
                    }
                }
            }
        }
    }
}
