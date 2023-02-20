package com.jgeniselli.banco.core.repository

import kotlinx.coroutines.flow.StateFlow

typealias NameAndColor = Pair<String, ULong>

interface GameRepository {
    val players: StateFlow<List<Player>>
    val history: StateFlow<List<OperationLog>>
    suspend fun startGame(players: List<NameAndColor>, initialBalance: Double = 1000.0)
    suspend fun credit(playerId: Int, value: Double)
    suspend fun debit(playerId: Int, value: Double)
    suspend fun transfer(sourceId: Int, recipientId: Int, value: Double)
}
