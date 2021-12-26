package com.jgeniselli.banco.game.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.game.play.ui.theme.BancoImobiliarioTheme

@Composable
fun ComposableTextDialogContent(
    message: String,
    value: String,
    label: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
) {
//    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = message
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            label = { Text(text = label) }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewContent() {
    BancoImobiliarioTheme {
        ComposableTextDialogContent(
            message = "This is a message that probably uses more than a single line",
            value = "R$ 2.000,00",
            label = "Label",
            onValueChange = { },
            onDone = { }
        )
    }
}

@Composable
@Preview(name = "Error", showBackground = true)
private fun PreviewContentError() {
    BancoImobiliarioTheme {
        ComposableTextDialogContent(
            message = "This is a message that probably uses more than a single line",
            value = "R$ 2.000,00",
            label = "Label",
            onValueChange = { },
            onDone = { }
        )
    }
}

@Composable
@Preview(name = "Error", showBackground = true)
private fun PreviewContentEmpty() {
    BancoImobiliarioTheme {
        ComposableTextDialogContent(
            message = "This is a message that probably uses more than a single line",
            value = "",
            label = "Label",
            onValueChange = { },
            onDone = { }
        )
    }
}