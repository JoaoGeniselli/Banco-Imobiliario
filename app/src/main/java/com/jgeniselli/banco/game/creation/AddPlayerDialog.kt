package com.jgeniselli.banco.game.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

const val NAME_MINIMUM_LENGTH = 2

@Composable
fun AddPlayerDialogLoader(
    onDone: (name: String) -> Unit,
    forbiddenNames: List<String> = listOf(),
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Surface(shape = MaterialTheme.shapes.large) {
            AddPlayerDialog(
                modifier = Modifier.padding(16.dp),
                forbiddenNames = forbiddenNames,
                onDone = onDone
            )
        }
    }
}

@Composable
fun AddPlayerDialog(
    modifier: Modifier = Modifier,
    forbiddenNames: List<String>,
    onDone: (name: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Column(modifier.fillMaxWidth()) {
        Text(text = "Insert the Player's name", style = MaterialTheme.typography.h6)
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = name,
            onValueChange = { name = it },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (isValidName(name, forbiddenNames)) onDone(name) }
            ),
            label = { Text(text = "Name") }
        )

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            enabled = isValidName(name, forbiddenNames),
            onClick = { onDone(name) }
        ) {
            Text(text = "OK")
        }

        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}

private fun isValidName(name: String, forbidden: List<String>) =
    name.length >= NAME_MINIMUM_LENGTH && forbidden.contains(name).not()

@Preview(showBackground = true)
@Composable
private fun PreviewAddPlayerDialog() {
    Surface(color = Color.White) {
        AddPlayerDialog(
            modifier = Modifier.padding(16.dp),
            forbiddenNames = listOf()
        ) {}
    }
}