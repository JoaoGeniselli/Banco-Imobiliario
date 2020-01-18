package com.jgeniselli.banco.infra.db

import com.jgeniselli.banco.core.PlayerStorage
import com.jgeniselli.banco.core.StoredPlayerDto
import com.jgeniselli.banco.core.StoredTransactionDto

class DBPlayerStorage(
    private val gameDao: DBGameDao
) : PlayerStorage {

    override suspend fun findTransactionHistory(): List<StoredTransactionDto> {
        return gameDao.findTransactionsWithPlayerColor().map { it.toDto() }
    }

    private fun ColorAndCashTuple.toDto() = StoredTransactionDto(cash, color)

    override suspend fun findAllPlayers(): List<StoredPlayerDto> {
        return gameDao.findAll().map { it.toDto() }
    }

    override suspend fun findById(playerId: Long): StoredPlayerDto? {
        return gameDao.findById(playerId)?.toDto()
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

    override suspend fun addTransaction(playerId: Long, value: Double) {
        addCashToPlayer(playerId, value)
        insertTransaction(playerId, value)
    }

    private suspend fun addCashToPlayer(playerId: Long, value: Double) {
        findById(playerId)?.let {
            val updatedCash = it.currentCash + value
            gameDao.updateCash(playerId, updatedCash)
        }
    }

    private fun insertTransaction(playerId: Long, value: Double) {
        val transaction = DBTransaction(playerId = playerId, cash = value)
        gameDao.insertTransaction(transaction)
    }

    private fun DBPlayer.toDto(): StoredPlayerDto {
        return StoredPlayerDto(id, color, cash)
    }
}