package com.jgeniselli.banco.game.history

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SwapHoriz
import com.jgeniselli.banco.R
import com.jgeniselli.banco.core.repository.OperationLog
import com.jgeniselli.banco.ui.component.toCurrency

class OperationFormatter(
    private val context: Context
) {

    fun format(operationLog: OperationLog): FormattedOperation =
        when (operationLog) {
            is OperationLog.Transfer -> FormattedOperation(
                Icons.Default.SwapHoriz,
                operationLog.value.toCurrency(),
                context.getString(
                    R.string.history_transfer,
                    operationLog.source.name,
                    operationLog.recipient.name
                )
            )
            is OperationLog.Debit -> FormattedOperation(
                Icons.Default.Remove,
                operationLog.value.toCurrency(),
                operationLog.player.name
            )
            is OperationLog.Credit -> FormattedOperation(
                Icons.Default.Add,
                operationLog.value.toCurrency(),
                operationLog.player.name
            )
        }
}