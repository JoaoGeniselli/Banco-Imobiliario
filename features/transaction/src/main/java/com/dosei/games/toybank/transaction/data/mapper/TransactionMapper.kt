package com.dosei.games.toybank.transaction.data.mapper

import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import com.dosei.games.toybank.transaction.TransactionState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun TransactionState.toDatabaseEntity() = TransactionEntity(
    type = type.id,
    amountInCents = amountInCents,
    sourcePlayerId = playerId,
    destinationPlayerId = destinationPlayerId,
    timestamp = Clock.System.now().toEpochMilliseconds()
)