package com.jgeniselli.banco.compose

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Player(
    val id: Int,
    val name: String,
    val balance: Double
)

sealed class OperationLog {

    data class Credit(val player: Player, val value: Double) : OperationLog()
    data class Debit(val player: Player, val value: Double) : OperationLog()

    data class Transfer(
        val source: Player,
        val recipient: Player,
        val value: Double
    ) : OperationLog()
}

interface GameRepository {
    val players: StateFlow<List<Player>>
    val history: StateFlow<List<OperationLog>>
    suspend fun startGame(players: List<String>, initialBalance: Double = 1000.0)
    suspend fun credit(playerId: Int, value: Double)
    suspend fun debit(playerId: Int, value: Double)
    suspend fun transfer(sourceId: Int, recipientId: Int, value: Double)
}

class MemoryGameRepository : GameRepository {

    private val _players = MutableStateFlow(
        listOf(
            Player(1, "John", 1000.0),
            Player(2, "Peter", 1000.0),
            Player(3, "Alex", 1000.0),
        )
    )
    override val players: StateFlow<List<Player>> get() = _players

    private val _history = MutableStateFlow<List<OperationLog>>(emptyList())
    override val history: StateFlow<List<OperationLog>> get() = _history

    override suspend fun startGame(players: List<String>, initialBalance: Double) {
        _players.value = players.mapIndexed { index, name ->
            Player(
                id = index.inc(),
                name = name,
                balance = initialBalance
            )
        }
        _history.value = emptyList()
    }

    override suspend fun credit(playerId: Int, value: Double) {
        val player = playerById(playerId).apply { addBalance(value) }
        addToHistory(OperationLog.Credit(player, value))
    }

    override suspend fun debit(playerId: Int, value: Double) {
        val player = playerById(playerId).apply { removeBalance(value) }
        addToHistory(OperationLog.Debit(player, value))
    }

    override suspend fun transfer(sourceId: Int, recipientId: Int, value: Double) {
        val source = playerById(sourceId).apply { removeBalance(value) }
        val recipient = playerById(recipientId).apply { addBalance(value) }
        addToHistory(OperationLog.Transfer(source, recipient, value))
    }

    private fun playerById(id: Int) = _players.value.first { it.id == id }

    private fun addToHistory(log: OperationLog) = _history.update { old -> old + log }

    private fun Player.addBalance(value: Double) = update(
        this.copy(balance = balance + value)
    )

    private fun Player.removeBalance(value: Double) = addBalance(value * -1)

    private fun update(player: Player) = _players.update { old ->
        old.toMutableList().apply {
            val replaceIndex = indexOfFirst { it.id == player.id }
            set(replaceIndex, player)
        }
    }
}