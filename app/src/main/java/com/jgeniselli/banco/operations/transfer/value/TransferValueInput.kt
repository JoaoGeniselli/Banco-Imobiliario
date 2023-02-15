package com.jgeniselli.banco.operations.transfer.value

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.NumberInput
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransferValueInputLoader(
    viewModel: TransferViewModel = koinViewModel()
) {
    var value by remember { mutableStateOf(0.0) }
    var isDoneEnabled by remember { mutableStateOf(false) }

    TransferValueInput(
        balance = "R$ 2500,00",
        value = value,
        onUpdate = {
            value = it
            isDoneEnabled = value >= 0.01
        },
        isDoneEnabled = isDoneEnabled,
        onDone = { viewModel.onInputValue(value) }
    )
}

@Composable
fun TransferValueInput(
    modifier: Modifier = Modifier,
    balance: String,
    isDoneEnabled: Boolean,
    onDone: () -> Unit,
    onUpdate: (Double) -> Unit,
    value: Double
) {
    GenericInput(
        modifier = modifier,
        title = "Transaction Value",
        subtitle = "Current balance $balance",
        actionEnabled = isDoneEnabled,
        onAction = onDone
    ) {
        NumberInput(
            onUpdate = onUpdate,
            onDone = onDone,
            value = value,
            label = "Value"
        )

        LazyColumn {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransferValueInput() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransferValueInput(
            modifier = Modifier,
            balance = "$ 650,00",
            isDoneEnabled = true,
            onDone = {},
            onUpdate = {},
            value = 300.0
        )
    }
}