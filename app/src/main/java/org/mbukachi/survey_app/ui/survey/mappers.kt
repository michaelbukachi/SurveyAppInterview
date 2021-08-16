package org.mbukachi.survey_app.ui.survey

import org.mbukachi.domain.Question
import org.mbukachi.domain.QuestionType

fun Question.toUI(index: Int) = Question(
    id = index,
    questionText = questionText,
    answer = when (questionType) {
        QuestionType.SELECT_ONE -> {
            PossibleAnswer.SingleChoice(options.map {
                Option(label = it.key, value = it.value)
            })
        }
        QuestionType.FREE_TEXT -> {
            PossibleAnswer.InputChoice("")
        }
        QuestionType.TYPE_VALUE -> {
            PossibleAnswer.NumberInputChoice(0f)
        }
        else -> {
            PossibleAnswer.InputChoice("")
        }
    }
)