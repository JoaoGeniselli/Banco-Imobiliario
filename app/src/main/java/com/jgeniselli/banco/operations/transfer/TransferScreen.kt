package com.jgeniselli.banco.operations.transfer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
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
import com.jgeniselli.banco.operations.common.ValueInputShortcuts
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
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
        ) {
            item { Header(state.balance) }
            item { ValueInput(focusRequester, state.transferValue, onUpdate) }
            item { ValueInputShortcuts(onShortcut = onShortcut) }
            item { RecipientsTitle() }

            itemsIndexed(
                items = state.availableRecipients,
                key = { _, item -> item.name },
            ) { index, player ->
                PlayerRow(
                    modifier = Modifier.padding(top = 8.dp),
                    player = player, onClick = { onSelectRecipient(index) }
                )
            }
        }
        ContinueButton(
            state.isDoneAvailable, onDone
        )
    }
    LaunchedEffect(focusRequester) { focusRequester.requestFocus() }
}

@Composable
private fun ValueInput(
    focusRequester: FocusRequester,
    value: Double,
    onUpdate: (Double) -> Unit
) {
    val focusManager = LocalFocusManager.current
    NumberInput(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(top = 16.dp),
        onUpdate = onUpdate,
        onDone = { focusManager.clearFocus() },
        value = value,
        label = stringResource(id = R.string.value_input_label)
    )
}

@Composable
private fun Header(balance: Double) {
    Text(
        style = MaterialTheme.typography.h4,
        text = stringResource(R.string.transfer_title)
    )
    Text(
        style = MaterialTheme.typography.subtitle1,
        text = stringResource(R.string.current_balance, balance.toCurrency())
    )
}

@Composable
private fun RecipientsTitle() {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(R.string.recipient),
        style = MaterialTheme.typography.h5
    )
}

@Composable
private fun ContinueButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = isEnabled,
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.action_continue))
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
                availableRecipients = (1..15).map {
                    PlayerSummary("Player $it", Color.LightGray)
                },
                transferValue = 150.0,
                isDoneAvailable = true,
                isOperationDone = false
            ),
            onSelectRecipient = {},
            onShortcut = {}
        )
    }
}