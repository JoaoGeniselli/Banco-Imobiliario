package com.jgeniselli.banco.infra.memory

import com.jgeniselli.banco.core.PlayerStorage
import com.jgeniselli.banco.core.StoredPlayerDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoryPlayerStorage : PlayerStorage {

    private val players = mutableMapOf<Long, PlayerRegister>()

    override suspend fun findAllPlayers(): List<StoredPlayerDto> {
        return players.map { it.value.toDto() }
    }

    override suspend fun findById(playerId: Long): StoredPlayerDto? {
        return players[playerId]?.toDto()
    }

    override suspend fun updateCash(playerId: Long, updatedCash: Double) {
        players[playerId]?.cash = updatedCash
    }

    override suspend fun updateCashToAllPlayers(cash: Double) {
        players.values.forEach { it.cash = cash }
    }

    override suspend fun clearPlayers() {
        players.clear()
    }

    override suspend fun createPlayersForColors(colors: List<String>, initialCash: Double) {
        colors.forEachIndexed { index, color ->
            val id = index.toLong() + 1
            players[id] = PlayerRegister(id, color, initialCash)
        }
    }

    data class PlayerRegister(val id: Long, val color: String, var cash: Double) {

        fun toDto(): StoredPlayerDto {
            return StoredPlayerDto(id, color, cash)
        }
    }


}