package com.jgeniselli.banco.infra.db

import com.jgeniselli.banco.core.PlayerStorage
import com.jgeniselli.banco.core.StoredPlayerDto

class DBPlayerStorage(
    private val gameDao: DBGameDao
) : PlayerStorage {

    override suspend fun findAllPlayers(): List<StoredPlayerDto> {
        return gameDao.findAll().map { it.toDto() }
    }

    override suspend fun findById(playerId: Long): StoredPlayerDto? {
        return gameDao.findById(playerId)?.toDto()
    }

    override suspend fun updateCash(playerId: Long, updatedCash: Double) {
        gameDao.updateCash(playerId, updatedCash)
    }

    override suspend fun updateCashToAllPlayers(cash: Double) {
        gameDao.updateCashToAllPlayers(cash)
    }

    override suspend fun clearPlayers() {
        gameDao.deleteAllPlayers()
    }

    override suspend fun createPlayersForColors(colors: List<String>, initialCash: Double) {
        val newPlayers = colors.map {
            DBPlayer(color = it, cash = initialCash)
        }
        gameDao.insertAll(newPlayers)
    }

    private fun DBPlayer.toDto(): StoredPlayerDto {
        return StoredPlayerDto(id, color, cash)
    }
}