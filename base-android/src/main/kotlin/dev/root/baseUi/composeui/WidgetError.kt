package dev.root.baseUi.composeui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RepWidgetError(
    shouldShowError: Boolean, errorMessage: String,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = shouldShowError) {
        Text(modifier = Modifier.then(modifier), text = errorMessage)
    }
}