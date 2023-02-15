package com.jgeniselli.banco.operations.credit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jgeniselli.banco.compose.Player
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.NumberInput
import com.jgeniselli.banco.ui.component.toCurrency
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CreditValueScreen(
    playerId: Int,
    vm: CreditViewModel = koinViewModel { parametersOf(playerId) },
    onOperationDone: () -> Unit
) {
    val uiState by vm.state.collectAsState()

    LaunchedEffect(uiState.isOperationDone) {
        if (uiState.isOperationDone) {
            onOperationDone()
        }
    }

    CreditValueContent(
        state = uiState,
        onDone = vm::commitOperation,
        onUpdate = vm::updateValue,
    )
}

@Composable
fun CreditValueContent(
    modifier: Modifier = Modifier,
    state: CreditState,
    onDone: () -> Unit,
    onUpdate: (Double) -> Unit
) {
    GenericInput(
        modifier = modifier,
        title = "Credit Value",
        subtitle = "Current balance ${state.player.balance.toCurrency()}",
        actionEnabled = state.isDoneEnabled,
        onAction = onDone
    ) {
        NumberInput(
            onUpdate = onUpdate,
            onDone = { if (state.isDoneEnabled) onDone() },
            value = state.value,
            label = "Value"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCreditValueContent() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        CreditValueContent(
            modifier = Modifier,
            state = CreditState(
                player = Player(id = 0, name = "John", balance = 650.0)
            ),
            onUpdate = {},
            onDone = {}
        )
    }
}
