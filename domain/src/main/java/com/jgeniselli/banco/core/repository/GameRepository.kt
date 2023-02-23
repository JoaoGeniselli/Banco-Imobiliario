package com.jgeniselli.banco.core.repository

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player
import kotlinx.coroutines.flow.StateFlow

typealias NameAndColor = Pair<String, ULong>

const val DEFAULT_INITIAL_BALANCE = 25_000.00

interface GameRepository {
    val players: StateFlow<List<Player>>
    val history: StateFlow<List<OperationLog>>

    suspend fun startGame(
        players: List<NameAndColor>,
        initialBalance: Double = DEFAULT_INITIAL_BALANCE
    )
    suspend fun credit(playerId: Int, value: Double)
    suspend fun debit(playerId: Int, value: Double)
    suspend fun transfer(sourceId: Int, recipientId: Int, value: Double)

    fun playerById(id: Int): Player
}
