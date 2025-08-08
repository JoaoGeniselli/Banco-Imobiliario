package com.dosei.games.toybank.data.model

sealed interface Transaction {

    val amountInCents: Int
    val timestamp: Long

    data class Deposit(
        val playerId: Int,
        override val amountInCents: Int,
        override val timestamp: Long,
    ) : Transaction

    data class Withdraw(
        val playerId: Int,
        override val amountInCents: Int,
        override val timestamp: Long,
    ) : Transaction

    data class Transfer(
        val fromPlayerId: Int,
        val toPlayerId: Int,
        override val amountInCents: Int,
        override val timestamp: Long,
    ) : Transaction
}
