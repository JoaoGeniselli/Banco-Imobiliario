package com.jgeniselli.banco.core.repository

import com.jgeniselli.banco.core.entities.OperationLog
import com.jgeniselli.banco.core.entities.Player
import kotlinx.coroutines.flow.StateFlow

internal class GameRepositoryImpl(
    private val storage: GameStorage
) : GameRepository {

    override val players: StateFlow<List<Player>> = storage.players
    override val history: StateFlow<List<OperationLog>> = storage.history

    override suspend fun startGame(players: List<NameAndColor>, initialBalance: Double) {
        storage.run {
            clearHistory()
            clearPlayerList()
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