package com.jgeniselli.banco.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddPlayerDialogLoader(
    onDone: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Surface(shape = MaterialTheme.shapes.large) {
            AddPlayerDialog(modifier = Modifier.padding(16.dp), onDone = onDone)
        }
    }
}

@Composable
fun AddPlayerDialog(modifier: Modifier = Modifier, onDone: (name: String) -> Unit) {
    var name by remember { mutableStateOf("") }

    Column(modifier.fillMaxWidth()) {
        Text(text = "Insert the Player's name", style = MaterialTheme.typography.h6)
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (name.isNotEmpty()) onDone(name) }
            ),
            label = { Text(text = "Name") }
        )

        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp),
            enabled = name.length >= 3,
            onClick = { onDone(name) }
        ) {
            Text(text = "OK")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddPlayerDialog() {
    Surface(color = Color.White) {
        AddPlayerDialog(
            modifier = Modifier.padding(16.dp),
            onDone = {}
        )
    }
}