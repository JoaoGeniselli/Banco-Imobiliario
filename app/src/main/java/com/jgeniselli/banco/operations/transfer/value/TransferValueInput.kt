package com.jgeniselli.banco.operations.transfer.value

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.NumberInput

@Composable
fun TransferValueInputLoader(

) {

}

@Composable
fun TransferValueInput(modifier: Modifier = Modifier, balance: String) {
    GenericInput(
        modifier = modifier,
        title = "Transaction Value",
        subtitle = "Current balance $balance",
        actionEnabled = true,
        onAction = { /*TODO*/ }
    ) {
        var value by remember { mutableStateOf(0.0) }
        NumberInput(
            onUpdate = { value = it },
            onDone = { /*TODO*/ },
            value = value,
            label = "Value"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransferValueInput() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransferValueInput(
            modifier = Modifier,
            balance = "$ 650,00"
        )
    }
}