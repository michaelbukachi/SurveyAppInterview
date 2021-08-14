package com.chepsi.survey.app.presentation.utils

import android.view.View


fun View.hideVisibility() {
    this.visibility = View.GONE
}

fun View.showVisibility() {
    this.visibility = View.VISIBLE
}