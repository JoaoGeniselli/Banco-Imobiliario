package com.jgeniselli.banco.core.repository

import kotlinx.coroutines.flow.StateFlow

data class Player(
    val id: Int,
    val name: String,
    val color: ULong,
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
    suspend fun startGame(players: List<NameAndColor>, initialBalance: Double = 1000.0)
    suspend fun credit(playerId: Int, value: Double)
    suspend fun debit(playerId: Int, value: Double)
    suspend fun transfer(sourceId: Int, recipientId: Int, value: Double)
}

class GameRepositoryImpl(
    private val storage: GameStorage
) : GameRepository {

    override val players: StateFlow<List<Player>> = storage.players
    override val history: StateFlow<List<OperationLog>> = storage.history

    override suspend fun startGame(players: List<NameAndColor>, initialBalance: Double) {
        storage.run {
            clearHistory()
            addPlayers(players, initialBalance)
        }
    }

    override suspend fun credit(playerId: Int, value: Double) {
        val player = playerById(playerId)
        val updatedBalance = player.balance + value
        storage.run {
            updateBalance(playerId, updatedBalance)
            addToHistory(OperationLog.Credit(player, value))
        }
    }

    override suspend fun debit(playerId: Int, value: Double) {
        val player = playerById(playerId)
        val updatedBalance = player.balance - value
        storage.run {
            updateBalance(playerId, updatedBalance)
            addToHistory(OperationLog.Debit(player, value))
        }
    }

    override suspend fun transfer(sourceId: Int, recipientId: Int, value: Double) {
        val source = playerById(sourceId)
        val updatedSourceBalance = source.balance - value

        val recipient = playerById(sourceId)
        val updatedRecipientBalance = source.balance + value

        storage.run {
            updateBalance(sourceId, updatedSourceBalance)
            updateBalance(recipientId, updatedRecipientBalance)
            addToHistory(OperationLog.Transfer(source, recipient, value))
        }
    }

    private fun playerById(id: Int) = players.value.first { it.id == id }
}
