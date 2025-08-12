package com.dosei.games.toybank.transaction.data.mapper

import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import com.dosei.games.toybank.core.toolbox.now
import com.dosei.games.toybank.transaction.TransactionState

fun TransactionState.toDatabaseEntity() = TransactionEntity(
    type = type.id,
    amountInCents = amountInCents,
    sourcePlayerId = playerId,
    destinationPlayerId = destinationPlayerId,
    timestamp = now()
)