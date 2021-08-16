package org.mbukachi.survey_app.ui.survey

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.mbukachi.survey_app.ui.theme.SurveyAppTheme


@Composable
fun Question(
    question: QuestionUI,
    answer: AnswerUI<*>?,
    onAnswer: (AnswerUI<*>) -> Unit,
    modifier: Modifier = Modifier
) {
    QuestionContent(question, answer, onAnswer, modifier)
}

@Composable
private fun QuestionContent(
    question: QuestionUI,
    answer: AnswerUI<*>?,
    onAnswer: (AnswerUI<*>) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
            QuestionTitle(question.questionText)
            Spacer(modifier = Modifier.height(24.dp))
            when (question.answer) {
                is PossibleAnswer.SingleChoice -> SingleChoiceQuestion(
                    possibleAnswer = question.answer,
                    answer = answer as AnswerUI.SingleChoice?,
                    onAnswerSelected = { answer -> onAnswer(AnswerUI.SingleChoice(answer)) },
                    modifier = Modifier.fillParentMaxWidth()
                )
                is PossibleAnswer.InputChoice -> InputQuestion(
                    possibleAnswer = question.answer,
                    answer = answer as AnswerUI.Input?,
                    onAction = { answer ->
                        onAnswer(AnswerUI.Input(answer = answer))

                    }
                )
                is PossibleAnswer.NumberInputChoice -> NumberInputQuestion(
                    possibleAnswer = question.answer,
                    answer = answer as AnswerUI.NumberInput?,
                    onAction = { answer ->
                        onAnswer(AnswerUI.NumberInput(answer = answer))

                    }
                )
            }
        }
    }
}

@Composable
private fun QuestionTitle(title: String) {
    val backgroundColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.06f)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        )
    }
}

@Composable
private fun SingleChoiceQuestion(
    possibleAnswer: PossibleAnswer.SingleChoice,
    answer: AnswerUI.SingleChoice?,
    onAnswerSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val radioOptions = possibleAnswer.options.map { it.label }

    val selected = answer?.answer

    val (selectedOption, onOptionSelected) = remember(answer) { mutableStateOf(selected) }

    Column(modifier = modifier) {
        radioOptions.forEachIndexed { index, text ->
            val onClickHandle = {
                onOptionSelected(text)
                onAnswerSelected(possibleAnswer.options[index].value)
            }
            val optionSelected = text == selectedOption

            val answerBorderColor = if (optionSelected) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            }
            val answerBackgroundColor = if (optionSelected) {
                MaterialTheme.colors.primary.copy(alpha = 0.12f)
            } else {
                MaterialTheme.colors.background
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = answerBorderColor
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = optionSelected,
                            onClick = onClickHandle
                        )
                        .background(answerBackgroundColor)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = text
                    )

                    RadioButton(
                        selected = optionSelected,
                        onClick = onClickHandle,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun InputQuestion(
    possibleAnswer: PossibleAnswer.InputChoice,
    answer: AnswerUI.Input?,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text ,
        onValueChange = { text = it
                        onAction(text) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun NumberInputQuestion(
    possibleAnswer: PossibleAnswer.NumberInputChoice,
    answer: AnswerUI.NumberInput?,
    onAction: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text ,
        onValueChange = { text = it
            if (it.isNotEmpty()){
                onAction(text.toFloat())
            }
                        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview
@Composable
fun QuestionPreview() {
    val question = QuestionUI(
        id = "abc",
        questionText = "Select gender",
        answer = PossibleAnswer.SingleChoice(
            options = listOf(
                OptionUI(label = "Male", value = "male"),
                OptionUI(label = "Female", value = "female"),
            )
        ),
    )
    SurveyAppTheme {
        Question(
            question = question,
            answer = null,
            onAnswer = {},
        )
    }
}
