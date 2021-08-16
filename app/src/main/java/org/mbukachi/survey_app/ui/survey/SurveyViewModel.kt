package org.mbukachi.survey_app.ui.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.mbukachi.domain.*
import org.mbukachi.domain.Question

class SurveyViewModel(
    private val surveyRepo: SurveyRepo
) : ViewModel() {

    private val mutableUiState = MutableStateFlow<SurveyState>(SurveyState.Loading)
    val uiState: StateFlow<SurveyState>
        get() = mutableUiState

    private lateinit var surveyInitialState: SurveyState

    fun fetchSurveys() {
        viewModelScope.launch {
            surveyRepo.getSurvey().collect {
                when (val result = it) {
                    is DataResult.Success -> {
                        val orderedQuestions = mutableListOf(result.survey.startQuestion)
                        var currentQuestion: Question? = result.survey.startQuestion
                        while (currentQuestion != null && !currentQuestion.isLast()) {
                            currentQuestion = result.survey.getNextQuestion(currentQuestion)
                            if (currentQuestion != null) {
                                orderedQuestions.add(currentQuestion)
                            }
                        }

                        val questions: List<QuestionState> =
                            orderedQuestions.mapIndexed { index, question ->
                                val showPrevious = index > 0
                                val showDone = index == orderedQuestions.size - 1
                                QuestionState(
                                    question = question.toUI(index = index),
                                    questionIndex = index,
                                    totalQuestionsCount = orderedQuestions.size,
                                    showPrevious = showPrevious,
                                    showDone = showDone
                                )
                            }

                        surveyInitialState = SurveyState.Questions(result.survey.id, questions)
                        mutableUiState.value = surveyInitialState
                    }
                }

            }
        }
    }

    fun submitResponse(surveyQuestions: SurveyState.Questions) {
        mutableUiState.value = SurveyState.Done(surveyQuestions.surveyId, Response("abc"))
//        val answers = surveyQuestions.questionsState.mapNotNull { it.answer }
//        val result = surveyRepository.getSurveyResult(answers)
//        _uiState.value = SurveyState.Result(surveyQuestions.surveyTitle, result)
    }

    fun resetState() {
       mutableUiState.value = SurveyState.RestartSurvey
    }
}
