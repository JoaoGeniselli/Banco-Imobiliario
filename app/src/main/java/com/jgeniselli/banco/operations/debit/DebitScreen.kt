package com.jgeniselli.banco.operations.debit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgeniselli.banco.R
import com.jgeniselli.banco.ui.component.GenericInput
import com.jgeniselli.banco.ui.component.NumberInput
import com.jgeniselli.banco.ui.component.toCurrency
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
    )
}

@Composable
fun DebitContent(
    modifier: Modifier = Modifier,
    state: DebitState,
    onDone: () -> Unit,
    onUpdate: (Double) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    GenericInput(
        modifier = modifier,
        title = stringResource(R.string.debit_title),
        subtitle = stringResource(R.string.current_balance, state.balance.toCurrency()),
        actionEnabled = state.isDoneEnabled,
        onAction = onDone
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                imageVector = Icons.Default.AttachMoney,
                contentDescription = null
            )
            NumberInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                onUpdate = onUpdate,
                onDone = { if (state.isDoneEnabled) onDone() },
                value = state.value,
                label = stringResource(R.string.value_input_label)
            )
        }
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
            onDone = {}
        )
    }
}
