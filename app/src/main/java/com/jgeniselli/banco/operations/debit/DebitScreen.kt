package com.jgeniselli.banco.operations.debit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.operations.common.component.ValueInputShortcuts
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.NumberInput
import com.jgeniselli.banco.ui.toolbox.toCurrency
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DebitScreen(
    playerId: Int,
    viewModel: DebitViewModel = koinViewModel { parametersOf(playerId) },
    onOperationDone: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(uiState.isOperationDone) {
        if (uiState.isOperationDone) {
            onOperationDone()
        }
    }

    DebitContent(
        state = uiState,
        onDone = viewModel::commitOperation,
        onUpdate = viewModel::updateValue,
        onShortcut = viewModel::onShortcut
    )
}

@Composable
fun DebitContent(
    modifier: Modifier = Modifier,
    state: DebitState,
    onDone: () -> Unit,
    onUpdate: (Double) -> Unit,
    onShortcut: (Double) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    GenericInput(
        modifier = modifier,
        title = stringResource(R.string.debit_title),
        subtitle = stringResource(R.string.current_balance, state.balance.toCurrency()),
        actionEnabled = state.isDoneEnabled,
        onAction = onDone
    ) {
        NumberInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .focusRequester(focusRequester),
            onUpdate = onUpdate,
            onDone = {
                focusManager.clearFocus()
                if (state.isDoneEnabled) onDone()
            },
            value = state.value,
            label = stringResource(R.string.value_input_label)
        )
        ValueInputShortcuts(onShortcut = onShortcut)
    }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDebitScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        DebitContent(
            modifier = Modifier,
            state = DebitState(650.0),
            onUpdate = {},
            onDone = {},
            onShortcut = {}
        )
    }
}
