package com.jgeniselli.banco.core

interface PlayerStorage {
    suspend fun findAllPlayers(): List<StoredPlayerDto>
    suspend fun findById(playerId: Long) : StoredPlayerDto?
    suspend fun updateCash(playerId: Long, updatedCash: Double)
    suspend fun updateCashToAllPlayers(cash: Double)
    suspend fun clearPlayers()
    suspend fun createPlayersForColors(colors: List<String>, initialCash: Double)
}

