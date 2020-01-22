package com.jgeniselli.banco.infra.db

import com.jgeniselli.banco.core.*
import com.jgeniselli.banco.infra.concurrency.runInIO
import com.jgeniselli.banco.infra.concurrency.runInMain

class DBPlayerStorage(
    private val gameDao: DBGameDao
) : PlayerStorage {

    override fun findAllPlayers(callback: Callback<List<StoredPlayerDto>>) {
        runInIO {
            val players = gameDao.findAllPlayers().map { it.toDto() }
            runInMain { callback(players) }
        }
    }

    override fun findById(playerId: Long, callback: Callback<StoredPlayerDto?>) {
        runInIO {
            val player = gameDao.findPlayerById(playerId)?.toDto()
            runInMain { callback(player) }
        }
    }

    override fun updateCashToAllPlayers(cash: Double, callback: ResultlessCallback) {
        runInIO {
            gameDao.updateCashToAllPlayers(cash)
            runInMain(callback)
        }
    }

    override fun clearPlayersAndTransactions(callback: ResultlessCallback) {
        runInIO {
            gameDao.deleteAllTransactions()
            gameDao.deleteAllPlayers()
            runInMain(callback)
        }
    }

    override fun createPlayersForColors(colors: List<String>, initialCash: Double, callback: ResultlessCallback) {
        runInIO {
            val newPlayers = colors.map {
                DBPlayer(color = it, cash = initialCash)
            }
            gameDao.insertAll(newPlayers)
            runInMain(callback)
        }
    }

    override fun isGameGoingOn(callback: Callback<Boolean>) {
        runInIO {
            val hasGame = gameDao.findAllPlayers().isNotEmpty()
            runInMain { callback(hasGame) }
        }
    }

    override fun findTransactionHistory(callback: Callback<List<StoredTransactionDto>>) {
        runInIO {
            val tuples = gameDao.findTransactionsWithPlayerColor()
            val history = tuples.map { it.toDto() }
            runInMain { callback(history) }
        }
    }

    private fun ColorAndCashTuple.toDto() = StoredTransactionDto(cash, color)

    override fun addTransaction(playerId: Long, value: Double, callback: ResultlessCallback) {
        runInIO {
            addCashToPlayer(playerId, value)
            insertTransaction(playerId, value)
            runInMain(callback)
        }
    }

    private fun addCashToPlayer(playerId: Long, value: Double) {
        gameDao.findPlayerById(playerId)?.let {
            val updatedCash = it.cash + value
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