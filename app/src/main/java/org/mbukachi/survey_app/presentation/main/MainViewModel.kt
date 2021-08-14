package com.chepsi.survey.app.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chepsi.survey.app.data.network.models.ResponseDTO
import com.chepsi.survey.app.domain.repos.Answer
import com.chepsi.survey.app.domain.repos.Survey
import com.chepsi.survey.app.domain.repos.SurveyResponse
import com.chepsi.survey.app.domain.usecases.AnsweredSurveysUseCase
import com.chepsi.survey.app.domain.usecases.SubmitSurveyUseCase
import com.chepsi.survey.app.domain.usecases.SurveyUseCase
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val surveyUseCase: SurveyUseCase,
    private val answeredSurveysUseCase: AnsweredSurveysUseCase,
    private val submitSurveyUseCase: SubmitSurveyUseCase
) : ViewModel() {
    private val _survey = MutableLiveData<Survey>()
    val survey: LiveData<Survey> get() = _survey
    var surveyResponses = ResponseDTO()
    val currentPosition = MutableLiveData<Int>()
    private val _saveSurveyResponses = MutableLiveData<List<SurveyResponse>>()
    val saveSurveyResponses: LiveData<List<SurveyResponse>> get() = _saveSurveyResponses

    fun fetchSurvey() {
        viewModelScope.launch {
            surveyUseCase.invoke(Unit).collect {
                if (it != null) {
                    _survey.value = it
                }
            }
        }
    }

    fun getQuestion(position: Int) =
        survey.value?.questions?.get(position)!!

    fun resetResponse() {
        surveyResponses = ResponseDTO()
    }

    fun saveAnswer(surveyAnswers: Answer) {
        surveyResponses.answers.add(surveyAnswers)
    }

    fun saveResponse(surveyResponse: SurveyResponse) {
        viewModelScope.launch {
            submitSurveyUseCase.invoke(surveyResponse)
        }
    }

    fun fetchResponses() {
        viewModelScope.launch {
            answeredSurveysUseCase.invoke(Unit).collect {
                _saveSurveyResponses.value = it
            }

        }
    }
}