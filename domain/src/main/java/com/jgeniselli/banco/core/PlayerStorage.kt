package com.jgeniselli.banco.core

interface PlayerStorage {
    fun findAllPlayers(callback: Callback<List<StoredPlayerDto>>)
    fun findById(playerId: Long, callback: Callback<StoredPlayerDto?>)
    fun updateCashToAllPlayers(cash: Double, callback: ResultlessCallback)
    fun clearPlayersAndTransactions(callback: ResultlessCallback)
    fun createPlayersForColors(colors: List<String>, initialCash: Double, callback: ResultlessCallback)
    fun addTransaction(playerId: Long, value: Double, callback: ResultlessCallback)
    fun findTransactionHistory(callback: (List<StoredTransactionDto>) -> Unit)
    fun isGameGoingOn(callback: Callback<Boolean>)
}

