package com.jgeniselli.banco.core.repository

import com.jgeniselli.banco.core.entities.OperationLog
import com.jgeniselli.banco.core.entities.Player
import kotlinx.coroutines.flow.StateFlow

interface GameStorage {
    val players: StateFlow<List<Player>>
    val history: StateFlow<List<OperationLog>>

    suspend fun isOngoingGameAvailable(): Boolean
    suspend fun clearPlayerList()
    suspend fun addPlayers(players: List<NameAndColor>, balance: Double)
    suspend fun updateBalance(playerId: Int, value: Double)
    suspend fun addToHistory(operation: OperationLog)
    suspend fun clearHistory()
}

