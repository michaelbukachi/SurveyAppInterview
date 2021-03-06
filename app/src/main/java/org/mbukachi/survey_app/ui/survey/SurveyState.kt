package org.mbukachi.survey_app.ui.survey

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class QuestionState(
    val question: QuestionUI,
    val questionIndex: Int,
    val totalQuestionsCount: Int,
    val showPrevious: Boolean,
    val showDone: Boolean
) {
    var enableNext by mutableStateOf(false)
    var answer by mutableStateOf<AnswerUI<*>?>(null)
}

sealed class SurveyState {
    data class Questions(
        val surveyId: String,
        val questionsState: List<QuestionState>
    ) : SurveyState() {
        var currentQuestionIndex by mutableStateOf(0)
    }

    object Done : SurveyState()
    object Loading : SurveyState()
    object RestartSurvey : SurveyState()
}