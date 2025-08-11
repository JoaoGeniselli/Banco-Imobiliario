package com.dosei.games.toybank.core.data.mapper

import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import com.dosei.games.toybank.core.data.model.Transaction

const val TRANSACTION_TYPE_DEPOSIT = 'D'
const val TRANSACTION_TYPE_TRANSFER = 'T'
const val TRANSACTION_TYPE_WITHDRAW = 'W'

fun Transaction.toDatabaseEntity() = when (this) {
    is Transaction.Deposit -> TransactionEntity(
        type = TRANSACTION_TYPE_DEPOSIT,
        sourcePlayerId = playerId,
        amountInCents = amountInCents,
        timestamp = timestamp
    )
    is Transaction.Withdraw -> TransactionEntity(
        type = TRANSACTION_TYPE_WITHDRAW,
        sourcePlayerId = playerId,
        amountInCents = amountInCents,
        timestamp = timestamp
    )
    is Transaction.Transfer -> TransactionEntity(
        type = TRANSACTION_TYPE_TRANSFER,
        sourcePlayerId = fromPlayerId,
        destinationPlayerId = toPlayerId,
        amountInCents = amountInCents,
        timestamp = timestamp
    )
}

fun TransactionEntity.toTransaction(): Transaction = when (type) {
    TRANSACTION_TYPE_DEPOSIT -> Transaction.Deposit(
        playerId = sourcePlayerId,
        amountInCents = amountInCents,
        timestamp = timestamp
    )
    TRANSACTION_TYPE_WITHDRAW -> Transaction.Withdraw(
        playerId = sourcePlayerId,
        amountInCents = amountInCents,
        timestamp = timestamp
    )
    TRANSACTION_TYPE_TRANSFER -> Transaction.Transfer(
        fromPlayerId = sourcePlayerId,
        toPlayerId = destinationPlayerId!!,
        amountInCents = amountInCents,
        timestamp = timestamp
    )
    else -> error("Unknown transaction type: $type")
}