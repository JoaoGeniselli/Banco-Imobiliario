package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.mapper.toDatabaseEntity
import com.dosei.games.toybank.core.data.model.Transaction
import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val playerDao: PlayerDao,
    private val transactionDao: TransactionDao
) {
    val players = playerDao.fetchAllPlayers()

    suspend fun deposit(
        playerId: Int,
        amountInCents: Int
    ) {
        val playerBalance = playerDao.fetchPlayerBalance(playerId)
        val updatedBalance = playerBalance + amountInCents
        playerDao.updatePlayerBalance(playerId, updatedBalance)
    }

    suspend fun withdraw(
        playerId: Int,
        amountInCents: Int
    ) {
        deposit(playerId, amountInCents * -1)
    }

    private suspend fun addToHistory(transaction: Transaction) {
        transactionDao.insertAll(transaction.toDatabaseEntity())
    }
}