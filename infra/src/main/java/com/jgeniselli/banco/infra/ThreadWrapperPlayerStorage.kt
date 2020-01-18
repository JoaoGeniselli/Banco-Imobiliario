package com.jgeniselli.banco.infra

import com.jgeniselli.banco.core.PlayerStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ThreadWrapperPlayerStorage(
    private val childStorage: PlayerStorage
) : PlayerStorage {

    override suspend fun findAllPlayers() = runInIOThread {
        childStorage.findAllPlayers()
    }

    override suspend fun findById(playerId: Long) = runInIOThread {
        childStorage.findById(playerId)
    }

    override suspend fun updateCash(playerId: Long, updatedCash: Double) = runInIOThread {
        childStorage.updateCash(playerId, updatedCash)
    }

    override suspend fun updateCashToAllPlayers(cash: Double) = runInIOThread {
        childStorage.updateCashToAllPlayers(cash)
    }

    override suspend fun clearPlayers() = runInIOThread {
        childStorage.clearPlayers()
    }

    override suspend fun createPlayersForColors(colors: List<String>, initialCash: Double) = runInIOThread {
        childStorage.createPlayersForColors(colors, initialCash)
    }

    private suspend fun <T> runInIOThread(block: suspend CoroutineScope.() -> T): T {
        return withContext(Dispatchers.IO, block)
    }
}