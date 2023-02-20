package com.jgeniselli.banco.core.repository

import kotlinx.coroutines.flow.StateFlow

interface GameStorage {
    val players: StateFlow<List<Player>>
    val history: StateFlow<List<OperationLog>>

    suspend fun clearPlayerList()
    suspend fun addPlayers(players: List<NameAndColor>, balance: Double)
    suspend fun updateBalance(playerId: Int, value: Double)
    suspend fun addToHistory(operation: OperationLog)
    suspend fun clearHistory()
}

