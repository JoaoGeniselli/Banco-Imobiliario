package com.jgeniselli.banco.core

interface PlayerStorage {
    suspend fun findAllPlayers(): List<StoredPlayerDto>
    suspend fun findById(playerId: Long): StoredPlayerDto?
    suspend fun updateCashToAllPlayers(cash: Double)
    suspend fun clearPlayersAndTransactions()
    suspend fun createPlayersForColors(colors: List<String>, initialCash: Double)
    suspend fun addTransaction(playerId: Long, value: Double)
    suspend fun findTransactionHistory(): List<StoredTransactionDto>
}

