package com.jgeniselli.banco.infra

import com.jgeniselli.banco.core.repository.GameStorage
import com.jgeniselli.banco.core.repository.NameAndColor
import com.jgeniselli.banco.core.entities.OperationLog
import com.jgeniselli.banco.core.entities.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class MemoryGameStorage : GameStorage {

    private val _players = MutableStateFlow(
        (1..6).map {
            Player(it, "Player $it", (0xff9e9e9eL.toULong() and 0xffffffffUL) shl 32, 25000.0)
        }
    )
    override val players: StateFlow<List<Player>> get() = _players

    private val _history = MutableStateFlow<List<OperationLog>>(emptyList())
    override val history: StateFlow<List<OperationLog>> get() = _history

    override suspend fun isOngoingGameAvailable() = true

    override suspend fun clearPlayerList() {
        _players.value = emptyList()
    }

    override suspend fun addPlayers(players: List<NameAndColor>, balance: Double) {
        _players.value = players.mapIndexed { index, (name, color) ->
            Player(
                id = index.inc(),
                name = name,
                color = color,
                balance = balance
            )
        }
    }

    override suspend fun updateBalance(playerId: Int, value: Double) {
        _players.update { old ->
            old.toMutableList().apply {
                val player = first { it.id == playerId }.copy(balance = value)
                val replaceIndex = indexOfFirst { it.id == playerId }
                set(replaceIndex, player)
            }
        }
    }

    override suspend fun addToHistory(operation: OperationLog) {
        _history.update { old -> old + operation }
    }

    override suspend fun clearHistory() {
        _history.value = emptyList()
    }
}