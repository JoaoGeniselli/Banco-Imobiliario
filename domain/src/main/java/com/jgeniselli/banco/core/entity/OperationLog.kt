package com.jgeniselli.banco.core.entity

sealed class OperationLog {

    data class Credit(val player: Player, val value: Double) : OperationLog()
    data class Debit(val player: Player, val value: Double) : OperationLog()

    data class Transfer(
        val source: Player,
        val recipient: Player,
        val value: Double
    ) : OperationLog()
}