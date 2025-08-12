package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    val history = transactionDao.fetchLastTransactions()

    suspend fun clearHistory() = transactionDao.clearAll()
}