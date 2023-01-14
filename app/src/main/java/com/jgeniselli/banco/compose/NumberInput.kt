package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.compose.ui.theme.BancoImobiliarioTheme

@Composable
fun NumberInput(
    modifier: Modifier = Modifier,
    onUpdate: (String) -> Unit,
    onDone: () -> Unit,
    value: String,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    Column(modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = value,
            onValueChange = onUpdate,
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
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
            value = "$ 250,00",
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
            value = "$ 250,00",
            label = "Test",
            isError = true,
            errorMessage = "Error message",
        )
    }
}