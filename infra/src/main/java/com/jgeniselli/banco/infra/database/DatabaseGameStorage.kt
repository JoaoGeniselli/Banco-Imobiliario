package com.jgeniselli.banco.infra.database

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player
import com.jgeniselli.banco.core.repository.GameStorage
import com.jgeniselli.banco.core.repository.NameAndColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DatabaseGameStorage(
    private val playerDao: PlayerDao,
    private val historyDao: HistoryDao,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : GameStorage {

    override val players: StateFlow<List<Player>> = playerDao
        .getAll()
        .map { storedPlayers -> storedPlayers.map { entity -> entity.toDomainPlayer() } }
        .stateIn(scope, SharingStarted.Eagerly, listOf())

    override val history: StateFlow<List<OperationLog>> = historyDao
        .getAllWithPlayers()
        .map { logs -> logs.map { it.toDomainOperation() } }
        .stateIn(scope, SharingStarted.Lazily, listOf())

    override suspend fun isOngoingGameAvailable(): Boolean = players.value.isNotEmpty()

    override suspend fun clearPlayerList() {
        scope.launch {
            playerDao.deleteAll()
        }
    }

    override suspend fun addPlayers(players: List<NameAndColor>, balance: Double) {
        scope.launch {
            val entities = players.map { (name, color) ->
                PlayerEntity(
                    name = name,
                    color = color.toLong(),
                    balance = balance
                )
            }
            playerDao.insertAll(entities)
        }
    }

    override suspend fun updateBalance(playerId: Int, value: Double) {
        scope.launch {
            playerDao.getById(playerId)
                ?.copy(balance = value)
                ?.let { playerDao.update(it) }
        }
    }

    override suspend fun addToHistory(operation: OperationLog) {
        scope.launch {
            historyDao.insertAll(operation.toEntity())
        }
    }

    override suspend fun clearHistory() {
        scope.launch {
            historyDao.deleteAll()
        }
    }
}