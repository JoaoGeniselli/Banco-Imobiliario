package com.dosei.games.toybank.history.data.model

import com.dosei.games.toybank.core.data.storage.player.Player
import java.util.Date

sealed interface HistoryEntry {

    val amount: Int
    val date: Date
    val sourcePlayer: Player

    data class Deposit(
        override val amount: Int,
        override val date: Date,
        override val sourcePlayer: Player,
    ) : HistoryEntry

    data class Withdraw(
        override val amount: Int,
        override val date: Date,
        override val sourcePlayer: Player,
    ) : HistoryEntry

    data class Transfer(
        override val amount: Int,
        override val date: Date,
        override val sourcePlayer: Player,
        val destinationPlayer: Player,
    ) : HistoryEntry
}