package com.dosei.games.toybank.data.repository

import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import com.dosei.games.toybank.data.mapper.toDatabaseEntity
import com.dosei.games.toybank.data.model.Transaction
import com.dosei.games.toybank.data.model.error.BusinessException
import com.dosei.games.toybank.data.model.error.ErrorCodes
import javax.inject.Inject

class GameplayRepository @Inject constructor(
    private val playerDao: PlayerDao,
    private val transactionDao: TransactionDao
) {
    val players = playerDao.fetchAllPlayers()
    val history = transactionDao.fetchLastTransactions()

    suspend fun commitTransaction(transaction: Transaction) {
        if (transaction.amountInCents <= 0) throw BusinessException(ErrorCodes.INVALID_TRANSACTION)

        when (transaction) {
            is Transaction.Deposit -> deposit(transaction)
            is Transaction.Transfer -> transfer(transaction)
            is Transaction.Withdraw -> withdraw(transaction)
        }
        addToHistory(transaction)
    }

    private suspend fun deposit(transaction: Transaction.Deposit) {
        val playerBalance = playerDao.fetchPlayerBalance(transaction.playerId)
        val updatedBalance = playerBalance + transaction.amountInCents
        playerDao.updatePlayerBalance(transaction.playerId, updatedBalance)
    }

    private suspend fun transfer(transaction: Transaction.Transfer) {
        val sourcePlayerBalance = playerDao.fetchPlayerBalance(transaction.fromPlayerId)
        val destinationPlayerBalance = playerDao.fetchPlayerBalance(transaction.toPlayerId)

        val updatedSourceBalance = sourcePlayerBalance - transaction.amountInCents
        val updatedDestinationBalance = destinationPlayerBalance + transaction.amountInCents

        playerDao.run {
            updatePlayerBalance(transaction.fromPlayerId, updatedSourceBalance)
            updatePlayerBalance(transaction.fromPlayerId, updatedDestinationBalance)
        }
    }

    private suspend fun withdraw(transaction: Transaction.Withdraw) {
        val playerBalance = playerDao.fetchPlayerBalance(transaction.playerId)
        val updatedBalance = playerBalance - transaction.amountInCents
        playerDao.updatePlayerBalance(transaction.playerId, updatedBalance)
    }

    private suspend fun addToHistory(transaction: Transaction) {
        transactionDao.insertAll(transaction.toDatabaseEntity())
    }
}