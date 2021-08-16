package org.mbukachi.survey_app.ui.survey.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.mbukachi.survey_app.ui.survey.Question
import org.mbukachi.survey_app.ui.survey.SurveyState
import org.mbukachi.survey_app.ui.utils.supportWideScreen

@Composable
fun SurveyQuestionsScreen(
    questions: SurveyState.Questions,
    onDonePressed: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val questionState = remember(questions.currentQuestionIndex) {
        questions.questionsState[questions.currentQuestionIndex]
    }

    Surface(modifier = Modifier.supportWideScreen()) {
        Scaffold(
            topBar = {
                SurveyTopAppBar(
                    questionIndex = questionState.questionIndex,
                    totalQuestionsCount = questionState.totalQuestionsCount,
                    onBackPressed = onBackPressed
                )
            },
            content = { innerPadding ->
                Question(
                    question = questionState.question,
                    answer = questionState.answer,
                    onAnswer = {
                        questionState.enableNext = true
                        questionState.answer = it
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            },
            bottomBar = {
                SurveyBottomBar(
                    questionState = questionState,
                    onPreviousPressed = { questions.currentQuestionIndex-- },
                    onNextPressed = { questions.currentQuestionIndex++ },
                    onDonePressed = onDonePressed
                )
            }
        )
    }
}
