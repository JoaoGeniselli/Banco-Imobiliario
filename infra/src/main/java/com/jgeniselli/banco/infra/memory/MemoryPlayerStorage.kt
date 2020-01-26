package com.jgeniselli.banco.infra.memory

import com.jgeniselli.banco.core.*
import com.jgeniselli.banco.core.boundary.PlayerStorage
import com.jgeniselli.banco.core.dto.StoredPlayerDto
import com.jgeniselli.banco.core.dto.StoredTransactionDto
import com.jgeniselli.banco.infra.concurrency.runInComputation
import com.jgeniselli.banco.infra.concurrency.runInMain

class MemoryPlayerStorage : PlayerStorage {

    private val players = mutableMapOf<Long, PlayerRegister>()
    private val transactions = mutableListOf<TransactionRegister>()

    override fun findAllPlayers(callback: Callback<List<StoredPlayerDto>>) {
        runInComputation {
            val players = players.map { it.value.toDto() }
            runInMain { callback(players) }
        }
        callback(players.map { it.value.toDto() })
    }

    override fun findById(playerId: Long, callback: Callback<StoredPlayerDto?>) {
        runInMain {
            callback(players[playerId]?.toDto())
        }
    }

    override fun updateCashToAllPlayers(cash: Double, callback: ResultlessCallback) {
        runInComputation {
            players.values.forEach { it.cash = cash }
            runInMain(callback)
        }
    }

    override fun clearPlayersAndTransactions(callback: ResultlessCallback) {
        runInComputation {
            transactions.clear()
            players.clear()
            runInMain(callback)
        }
    }

    override fun createPlayersForColors(colors: List<String>, initialCash: Double, callback: ResultlessCallback) {
        runInComputation {
            colors.forEachIndexed { index, color ->
                val id = index.toLong() + 1
                players[id] = PlayerRegister(id, color, initialCash)
            }
            runInMain(callback)
        }
    }

    override fun addTransaction(playerId: Long, value: Double, callback: ResultlessCallback) {
        runInComputation {
            players[playerId]?.let { player ->
                player.cash += value
                transactions.add(TransactionRegister(player.id, value))
            }
            runInMain(callback)
        }
    }

    override fun findTransactionHistory(callback: (List<StoredTransactionDto>) -> Unit) {
        runInComputation {
            val history = transactions.map { transaction ->
                val player = players[transaction.playerId]
                StoredTransactionDto(transaction.value, player?.color ?: "")
            }.reversed()
            runInMain { callback(history) }
        }
    }

    override fun isGameGoingOn(callback: Callback<Boolean>) {
        runInMain { callback(players.isNotEmpty()) }
    }

    data class PlayerRegister(val id: Long, val color: String, var cash: Double) {

        fun toDto(): StoredPlayerDto {
            return StoredPlayerDto(id, color, cash)
        }
    }

    data class TransactionRegister(val playerId: Long, var value: Double)


}