package com.chepsi.survey.app.data.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SurveyEntity(
    @PrimaryKey var id: String = "",
    var questions: RealmList<QuestionEntity> = RealmList(),
    var startQuestionId: String? = ""
) : RealmObject()

open class QuestionEntity(
    @PrimaryKey var id: String = "",
    var answerType: String? = "",
    var next: String? = "",
    var options: RealmList<OptionEntity> = RealmList(),
    var questionText: String? = "",
    var questionType: String? = ""
) : RealmObject()

open class OptionEntity(
    var displayText: String? = "",
    var value: String? = ""
) : RealmObject()


open class ResponseEntity(
    @PrimaryKey var id: String = "",
    var answers: RealmList<AnswerEntity> = RealmList()
) : RealmObject()

open class AnswerEntity(
    var question: String = "",
    var answer: String = ""
) : RealmObject()