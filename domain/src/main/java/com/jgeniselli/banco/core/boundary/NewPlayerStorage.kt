package com.jgeniselli.banco.core.boundary

import com.jgeniselli.banco.core.Player

interface NewPlayerStorage {
    suspend fun createPlayers(initialValue: Long, players: List<String>)
    suspend fun findAllPlayers(): List<Player>
    suspend fun findPlayerById(id: Int): Player?
    suspend fun updateBalance(playerId: Int, balance: Long)
    suspend fun clear()
}