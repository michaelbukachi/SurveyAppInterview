package com.chepsi.survey.app.data

import com.chepsi.survey.app.data.db.*
import com.chepsi.survey.app.domain.repos.*
import io.realm.RealmList


fun Survey.toDb(): SurveyEntity = SurveyEntity(
    id = id,
    questions = RealmList(*questions.map { question -> question.toDb() }.toTypedArray()),
    startQuestionId = startQuestionId
)

fun Question.toDb(): QuestionEntity = QuestionEntity(
    id = id,
    answerType = answerType,
    next = next,
    options = RealmList(*options.map { option -> option.toDb() }.toTypedArray()),
    questionText = questionText,
    questionType = questionType
)


fun Option.toDb(): OptionEntity = OptionEntity(
    displayText = displayText,
    value = value
)

fun SurveyResponse.toDb(): ResponseEntity = ResponseEntity(
    id = id,
    answers = RealmList(*answers.map { answer -> answer.toDb() }.toTypedArray())
)

fun Answer.toDb(): AnswerEntity = AnswerEntity(
    question = question,
    answer = answer
)

fun SurveyEntity.toSurvey() = Survey(
    id = id,
    questions = questions.map { questionEntity -> questionEntity.toQuestion() },
    startQuestionId = startQuestionId ?: ""
)

fun QuestionEntity.toQuestion() = Question(
    id = id,
    answerType = answerType ?: "",
    next = next ?: "",
    options = options.map { optionEntity -> optionEntity.toOption() },
    questionText = questionText ?: "",
    questionType = questionType ?: ""
)

fun OptionEntity.toOption() = Option(
    displayText = displayText ?: "",
    value = value ?: ""
)

fun ResponseEntity.toResponse(): SurveyResponse = SurveyResponse(
    id = id,
    answers = answers.map { answerEntity -> answerEntity.toAnswer() }
)

fun AnswerEntity.toAnswer(): Answer = Answer(
    question = question,
    answer = answer
)