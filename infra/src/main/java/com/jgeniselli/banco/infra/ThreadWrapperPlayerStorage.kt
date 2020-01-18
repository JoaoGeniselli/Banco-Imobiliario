package com.jgeniselli.banco.infra

import com.jgeniselli.banco.core.PlayerStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ThreadWrapperPlayerStorage(
    private val childStorage: PlayerStorage
) : PlayerStorage {

    override suspend fun addTransaction(playerId: Long, value: Double) = runInIOThread {
        childStorage.addTransaction(playerId, value)
    }

    override suspend fun findTransactionHistory() = runInIOThread {
        childStorage.findTransactionHistory()
    }

    override suspend fun findAllPlayers() = runInIOThread {
        childStorage.findAllPlayers()
    }

    override suspend fun findById(playerId: Long) = runInIOThread {
        childStorage.findById(playerId)
    }

    override suspend fun updateCashToAllPlayers(cash: Double) = runInIOThread {
        childStorage.updateCashToAllPlayers(cash)
    }

    override suspend fun clearPlayersAndTransactions() = runInIOThread {
        childStorage.clearPlayersAndTransactions()
    }

    override suspend fun createPlayersForColors(colors: List<String>, initialCash: Double) = runInIOThread {
        childStorage.createPlayersForColors(colors, initialCash)
    }

    override suspend fun isGameGoingOn() = runInIOThread {
        childStorage.isGameGoingOn()
    }

    private suspend fun <T> runInIOThread(block: suspend CoroutineScope.() -> T): T {
        return withContext(Dispatchers.IO, block)
    }
}