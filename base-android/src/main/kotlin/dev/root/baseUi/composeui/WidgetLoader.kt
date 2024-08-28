package dev.root.baseUi.composeui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RepWidgetLoader(shouldShowLoader: Boolean,
                            modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = shouldShowLoader) {
        RepWidgetLoader(modifier)
    }
}

@Composable
fun RepWidgetLoader(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}