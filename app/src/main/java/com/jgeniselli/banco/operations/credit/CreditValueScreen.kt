package com.jgeniselli.banco.operations.credit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jgeniselli.banco.R
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.NumberInput
import com.jgeniselli.banco.ui.component.toCurrency
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CreditValueScreen(
    playerId: Int,
    viewModel: CreditViewModel = koinViewModel { parametersOf(playerId) },
    onOperationDone: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(uiState.isOperationDone) {
        if (uiState.isOperationDone) {
            onOperationDone()
        }
    }

    CreditValueContent(
        state = uiState,
        onDone = viewModel::commitOperation,
        onUpdate = viewModel::updateValue,
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
        title = stringResource(R.string.credit_title),
        subtitle = stringResource(R.string.credit_subtitle, state.balance.toCurrency()),
        actionEnabled = state.isDoneEnabled,
        onAction = onDone
    ) {
        NumberInput(
            onUpdate = onUpdate,
            onDone = { if (state.isDoneEnabled) onDone() },
            value = state.value,
            label = stringResource(R.string.credit_input_label)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCreditValueContent() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        CreditValueContent(
            modifier = Modifier,
            state = CreditState(650.0),
            onUpdate = {},
            onDone = {}
        )
    }
}
