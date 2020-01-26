package com.jgeniselli.banco.core.boundary

import com.jgeniselli.banco.core.Callback
import com.jgeniselli.banco.core.ResultlessCallback
import com.jgeniselli.banco.core.dto.StoredPlayerDto
import com.jgeniselli.banco.core.dto.StoredTransactionDto

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

