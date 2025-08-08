package com.dosei.games.toybank.data.model

sealed interface Transaction {

    val timestamp: Long

    data class Deposit(
        val playerId: Int,
        val amountInCents: Int,
        override val timestamp: Long,
    ) : Transaction

    data class Withdraw(
        val playerId: Int,
        val amountInCents: Int,
        override val timestamp: Long,
    ) : Transaction

    data class Transfer(
        val fromPlayerId: Int,
        val toPlayerId: Int,
        val amountInCents: Int,
        override val timestamp: Long,
    ) : Transaction
}
