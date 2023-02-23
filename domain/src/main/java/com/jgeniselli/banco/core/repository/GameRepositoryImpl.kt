package com.jgeniselli.banco.core.repository

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player
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
        val player = this.playerById(playerId)
        val updatedBalance = player.balance + value
        storage.run {
            updateBalance(playerId, updatedBalance)
            addToHistory(OperationLog.Credit(player, value))
        }
    }

    override suspend fun debit(playerId: Int, value: Double) {
        val player = this.playerById(playerId)
        val updatedBalance = player.balance - value
        storage.run {
            updateBalance(playerId, updatedBalance)
            addToHistory(OperationLog.Debit(player, value))
        }
    }

    override suspend fun transfer(sourceId: Int, recipientId: Int, value: Double) {
        val source = this.playerById(sourceId)
        val updatedSourceBalance = source.balance - value

        val recipient = this.playerById(recipientId)
        val updatedRecipientBalance = recipient.balance + value

        storage.run {
            updateBalance(sourceId, updatedSourceBalance)
            updateBalance(recipientId, updatedRecipientBalance)
            addToHistory(OperationLog.Transfer(source, recipient, value))
        }
    }

    override fun playerById(id: Int): Player = players.value.first { it.id == id }
}