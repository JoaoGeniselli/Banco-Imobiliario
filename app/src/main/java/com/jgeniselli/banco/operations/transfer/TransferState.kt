package com.jgeniselli.banco.operations.transfer

import com.jgeniselli.banco.ui.component.PlayerSummary

data class TransferState(
    val balance: Double = 0.0,
    val availableRecipients: List<PlayerSummary> = emptyList(),
    val transferValue: Double = 0.0,
    val isDoneAvailable: Boolean = false,
    val isOperationDone: Boolean = false
)
