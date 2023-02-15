package com.jgeniselli.banco.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NumberInput(
    modifier: Modifier = Modifier,
    onUpdate: (Double) -> Unit,
    onDone: () -> Unit,
    value: Double = 0.0,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    val resolver = remember { CurrencyValueResolver() }
    val formattedValue = resolver.format(value)

    Column(modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = TextFieldValue(
                text = formattedValue,
                selection = TextRange(formattedValue.length)
            ),
            onValueChange = { newValue ->
                onUpdate(resolver.resolve(formattedValue, newValue.text))
            },
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                onDone()
                defaultKeyboardAction(ImeAction.Done)
            }),
            isError = isError
        )
        if (isError) {
            Text(
                text = errorMessage.orEmpty(),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
private fun PreviewCurrencyValueInput() {
    Surface(color = Color.White) {
        NumberInput(
            modifier = Modifier.padding(16.dp),
            onUpdate = {},
            value = 250.0,
            label = "Test",
            onDone = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
private fun PreviewCurrencyValueInputError() {
    Surface(color = Color.White) {
        NumberInput(
            modifier = Modifier.padding(16.dp),
            onUpdate = {},
            onDone = {},
            value = 250.0,
            label = "Test",
            isError = true,
            errorMessage = "Error message",
        )
    }
}