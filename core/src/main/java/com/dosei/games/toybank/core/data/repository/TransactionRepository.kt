package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    val history = transactionDao.fetchLastTransactions()

    suspend fun clearHistory() = transactionDao.clearAll()

    suspend fun addToHistory(entity: TransactionEntity) = transactionDao.insertAll(entity)
}