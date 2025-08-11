package com.dosei.games.toybank.transaction

import com.dosei.games.toybank.core.data.model.TransactionType

data class TransactionState(
    val type: TransactionType = TransactionType.DEPOSIT,
    val amountInCents: Int = 0,
    val playerId: Int = 0,
    val destinationPlayerId: Int? = null,
)