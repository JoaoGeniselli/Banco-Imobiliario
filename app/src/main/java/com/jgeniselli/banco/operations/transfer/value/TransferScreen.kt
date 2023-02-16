package com.jgeniselli.banco.operations.transfer.value

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
        onSelectRecipient = viewModel::selectRecipientAt
    )
}

@Composable
fun TransferContent(
    modifier: Modifier = Modifier,
    state: TransferState,
    onSelectRecipient: (Int) -> Unit,
    onUpdate: (Double) -> Unit,
    onDone: () -> Unit,
) {
    GenericInput(
        modifier = modifier,
        title = stringResource(R.string.transfer_title),
        subtitle = stringResource(R.string.current_balance, state.balance.toCurrency()),
        actionEnabled = state.isDoneAvailable,
        onAction = onDone
    ) {
        NumberInput(
            onUpdate = onUpdate,
            onDone = onDone,
            value = state.transferValue,
            label = stringResource(id = R.string.value_input_label)
        )
        Text(text = stringResource(R.string.recipient))
        PlayerList(
            players = state.availableRecipients,
            onClick = onSelectRecipient
        )
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
            onSelectRecipient = {}
        )
    }
}