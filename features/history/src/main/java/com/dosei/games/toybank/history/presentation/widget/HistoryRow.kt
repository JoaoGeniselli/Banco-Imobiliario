package com.dosei.games.toybank.history.presentation.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dosei.games.toybank.core.toolbox.format
import com.dosei.games.toybank.core.toolbox.formatBlr
import com.dosei.games.toybank.history.R
import com.dosei.games.toybank.history.data.model.HistoryEntry
import com.dosei.games.toybank.ui.theme.Blue500
import com.dosei.games.toybank.ui.theme.DeepOrange
import com.dosei.games.toybank.ui.theme.Green

@Composable
internal fun HistoryRow(entry: HistoryEntry) {
    when (entry) {
        is HistoryEntry.Deposit -> DepositRow(entry)
        is HistoryEntry.Transfer -> TransferRow(entry)
        is HistoryEntry.Withdraw -> WithdrawRow(entry)
    }
}

@Composable
private fun DepositRow(
    entry: HistoryEntry.Deposit,
) {
    CommonHistoryRow(
        entry = entry,
        icon = {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = stringResource(R.string.history_transaction_deposit),
                tint = Green
            )
        },
        name = { Text(entry.sourcePlayer.name) },
        operation = { Text(stringResource(R.string.history_transaction_deposit)) }
    )
}

@Composable
private fun TransferRow(
    entry: HistoryEntry.Transfer,
) {
    CommonHistoryRow(
        entry = entry,
        icon = {
            Icon(
                imageVector = Icons.Default.SyncAlt,
                contentDescription = stringResource(R.string.history_transaction_transfer),
                tint = Blue500
            )
        },
        name = {
            Text(
                stringResource(
                    R.string.history_template_from_to,
                    entry.sourcePlayer.name,
                    entry.destinationPlayer.name
                )
            )
        },
        operation = { Text(stringResource(R.string.history_transaction_transfer)) }
    )
}

@Composable
private fun WithdrawRow(
    entry: HistoryEntry.Withdraw,
) {
    CommonHistoryRow(
        entry = entry,
        icon = {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = stringResource(R.string.history_transaction_withdraw),
                tint = DeepOrange
            )
        },
        name = { Text(entry.sourcePlayer.name) },
        operation = { Text(stringResource(R.string.history_transaction_withdraw)) }
    )
}

@Composable
private fun CommonHistoryRow(
    entry: HistoryEntry,
    icon: @Composable () -> Unit,
    name: @Composable () -> Unit,
    operation: @Composable () -> Unit,
) {
    ListItem(
        leadingContent = icon,
        headlineContent = name,
        overlineContent = operation,
        trailingContent = { Text(entry.amount.formatBlr()) },
        supportingContent = { Text(entry.date.format()) }
    )
}