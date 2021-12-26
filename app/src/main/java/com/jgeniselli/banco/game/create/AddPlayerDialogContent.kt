package com.jgeniselli.banco.game.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.game.play.ui.theme.BancoImobiliarioTheme

@Composable
fun AddPlayerDialogContent(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    val error = remember { mutableStateOf(false) }
    val buttonEnabled = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Add New Player"
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Name") },
            value = value,
            onValueChange = {
                if (error.value) error.value = false
                if (it.isNotEmpty()) buttonEnabled.value = true
                onValueChange(it)
            },
            isError = error.value
        )
        Row(modifier = Modifier.align(Alignment.End)) {
            TextButton(onClick = onDone, enabled = buttonEnabled.value) { Text(text = "Add") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddPlayerDialogContent() {
    BancoImobiliarioTheme {
        AddPlayerDialogContent(
            value = "Johnny",
            onValueChange = {},
            onDone = {}
        )
    }
}