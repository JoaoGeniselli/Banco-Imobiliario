package com.dosei.games.toybank.history.data.mapper

import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import com.dosei.games.toybank.history.data.model.HistoryEntry
import java.util.Date

fun TransactionEntity.toHistoryEntry(players: List<Player>): HistoryEntry? {
    val sourcePlayer = players.first { it.id == sourcePlayerId }
    val date = Date(timestamp)
    return when (type) {
        TransactionType.DEPOSIT.id -> HistoryEntry.Deposit(amountInCents, date, sourcePlayer)
        TransactionType.WITHDRAW.id -> HistoryEntry.Withdraw(amountInCents, date, sourcePlayer)
        TransactionType.TRANSFER.id -> {
            val destinationPlayer = players.first { it.id == destinationPlayerId!! }
            HistoryEntry.Transfer(amountInCents, date, sourcePlayer, destinationPlayer)
        }
        else -> null
    }
}