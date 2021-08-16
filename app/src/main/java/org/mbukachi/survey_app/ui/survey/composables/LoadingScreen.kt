package org.mbukachi.survey_app.ui.survey.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.mbukachi.survey_app.ui.utils.supportWideScreen

@Composable
fun LoadingScreen() {
    Surface(modifier = Modifier.supportWideScreen()) {
        Scaffold(
            content = { innerPadding ->
                val modifier = Modifier.padding(innerPadding)
                Loader(modifier = modifier)
            },
        )
    }
}