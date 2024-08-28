package dev.root.baseUi.composeui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun RepWidgetTextArea(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholderValue: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = style,
        decorationBox = { textField ->
            Box(modifier = Modifier.padding(8.dp)) {
                if (value.text.isEmpty()) {
                    Text(
                        text = placeholderValue,
                        style = style,
                    )
                }
                textField()
            }
        }
    )
}