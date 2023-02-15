package com.jgeniselli.banco.operations.debit

data class DebitState(
    val balance: Double,
    val value: Double = 0.0,
    val isDoneEnabled: Boolean = false,
    val isOperationDone: Boolean = false
)