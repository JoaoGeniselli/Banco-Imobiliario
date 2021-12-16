package com.jgeniselli.banco.game.play

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.game.play.ui.theme.BancoImobiliarioTheme

@Composable
fun ComposableTransferDialogContent(
    message: String,
    valueLabel: String,
    value: String,
    onValueChange: (String) -> Unit,
    selectionLabel: String,
    selectedItem: String,
    items: List<String>,
    isErrorSelector: Boolean = false,
    isErrorValue: Boolean = false,
    isExpanded: Boolean,
    onDone: () -> Unit,
    onSelectItem: (String) -> Unit
) {
//    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = message
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            isError = isErrorSelector,
            label = { Text(text = selectionLabel) }
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = { }
        ) {
            items.forEach {
                DropdownMenuItem(onClick = { onSelectItem(it) }) { Text(text = it) }
            }
        }

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            value = value,
            onValueChange = onValueChange,
            isError = isErrorValue,
            label = { Text(text = valueLabel) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComposableTransferDialogContent() {
    BancoImobiliarioTheme {
        ComposableTransferDialogContent(
            message = "This is a message",
            valueLabel = "Value to be transfered",
            value = "R$ 1.500,00",
            onValueChange = {},
            selectionLabel = "Receiver",
            selectedItem = "",
            items = listOf("Player A", "Player B"),
            isExpanded = false,
            onDone = {},
            onSelectItem = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComposableTransferDialogContentExpanded() {
    BancoImobiliarioTheme {
        ComposableTransferDialogContent(
            message = "This is a message",
            valueLabel = "Value to be transfered",
            value = "R$ 1.500,00",
            onValueChange = {},
            selectionLabel = "Receiver",
            selectedItem = "",
            items = listOf("Player A", "Player B"),
            isExpanded = true,
            onDone = {},
            onSelectItem = {}
        )
    }
}