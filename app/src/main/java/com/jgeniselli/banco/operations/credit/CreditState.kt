package com.jgeniselli.banco.operations.credit

data class CreditState(
    val balance: Double,
    val value: Double = 0.0,
    val isDoneEnabled: Boolean = false,
    val isOperationDone: Boolean = false
)