package org.mbukachi.survey_app.ui.survey

import com.chepsi.survey.app.R

// Static data of questions
private val jetpackQuestions = mutableListOf(
    Question(
        id = 1,
        questionText = R.string.name_question,
        answer = PossibleAnswer.InputChoice(""),
        description = R.string.name_description
    ),
    Question(
        id = 2,
        questionText = R.string.pick_superhero,
        answer = PossibleAnswer.SingleChoice(
              listOf(
                  R.string.option_one,
                  R.string.option_two,
                  R.string.option_three),
        ),
        description = R.string.select_one
    ),
    Question(
        id = 3,
        questionText = R.string.takeaway,
        answer = PossibleAnswer.InputChoice(""),
        description = R.string.farm_size_description
    ),
)

private val jetpackSurvey = Survey(
    title = R.string.which_jetpack_library,
    questions = jetpackQuestions
)

object SurveyRepository {

    suspend fun getSurvey() = jetpackSurvey

    @Suppress("UNUSED_PARAMETER")
    fun getSurveyResult(answers: List<Answer<*>>): SurveyResult {
        return SurveyResult(
            library = "Compose",
            result = R.string.survey_result,
            description = R.string.survey_result_description
        )
    }
}
