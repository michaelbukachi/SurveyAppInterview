package org.mbukachi.domain

fun Question.isLast() = next == null

fun Survey.getNextQuestion(current: Question): Question? {
    if (current.isLast()) {
        return null
    }

    for (question in questions) {
        if (question.id == current.next) {
            return question
        }
    }

    return null
}