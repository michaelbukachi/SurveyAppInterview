package org.mbukachi.survey_app.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.mbukachi.survey_app.R
import java.security.InvalidParameterException

enum class Screen { Welcome, SignUp, SignIn, Survey }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.Survey -> {
            findNavController().navigate(R.id.surveyFragment)
        }
    }
}