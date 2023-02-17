package com.jgeniselli.banco.operations.transfer.value

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.jgeniselli.banco.ui.component.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TransferScreen(
    playerId: Int,
    viewModel: TransferViewModel = koinViewModel { parametersOf(playerId) },
    onOperationDone: () -> Unit
) {
    val state by viewModel.uiState.collectAsState(TransferState())

    LaunchedEffect(state.isOperationDone) {
        if (state.isOperationDone) {
            onOperationDone()
        }
    }

    TransferContent(
        state = state,
        onUpdate = viewModel::updateValue,
        onDone = viewModel::onDone,
        onSelectRecipient = viewModel::selectRecipientAt,
        onShortcut = viewModel::onShortcut
    )
}

@Composable
fun TransferContent(
    modifier: Modifier = Modifier,
    state: TransferState,
    onSelectRecipient: (Int) -> Unit,
    onUpdate: (Double) -> Unit,
    onShortcut: (Double) -> Unit,
    onDone: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    GenericInput(
        modifier = modifier.scrollable(scroll, Orientation.Vertical),
        title = stringResource(R.string.transfer_title),
        subtitle = stringResource(R.string.current_balance, state.balance.toCurrency()),
        actionEnabled = state.isDoneAvailable,
        onAction = onDone,
    ) {
        NumberInput(
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(top = 16.dp),
            onUpdate = onUpdate,
            onDone = { focusManager.clearFocus() },
            value = state.transferValue,
            label = stringResource(id = R.string.value_input_label)
        )
        Row(Modifier.padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ShortcutChip(value = +100.0, onShortcut = onShortcut)
            ShortcutChip(value = -100.0, onShortcut = onShortcut)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ShortcutChip(value = +1000.0, onShortcut = onShortcut)
            ShortcutChip(value = -1000.0, onShortcut = onShortcut)
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(R.string.recipient),
            style = MaterialTheme.typography.h5
        )
        PlayerList(
            modifier = Modifier.padding(top = 16.dp),
            players = state.availableRecipients,
            onClick = onSelectRecipient
        )
    }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTransferValueInput() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        TransferContent(
            modifier = Modifier,
            onDone = {},
            onUpdate = {},
            state = TransferState(
                balance = 5000.0,
                availableRecipients = listOf(
                    PlayerSummary("Player 1", Color.LightGray),
                    PlayerSummary("Player 2", Color.LightGray, true),
                    PlayerSummary("Player 3", Color.LightGray),
                ),
                transferValue = 150.0,
                isDoneAvailable = true,
                isOperationDone = false
            ),
            onSelectRecipient = {},
            onShortcut = {}
        )
    }
}