package dev.root.baseUi.composeui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RepWidgetAlert(
    message: String,
    confirmButton: String,
    confirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = confirmClick,
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick = confirmClick
            ) {
                Text(confirmButton)
            }
        },
    )
}