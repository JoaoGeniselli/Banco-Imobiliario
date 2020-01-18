package com.jgeniselli.banco.infra.memory

import com.jgeniselli.banco.core.PlayerStorage
import com.jgeniselli.banco.core.StoredPlayerDto
import com.jgeniselli.banco.core.StoredTransactionDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoryPlayerStorage : PlayerStorage {

    private val players = mutableMapOf<Long, PlayerRegister>()
    private val transactions = mutableListOf<TransactionRegister>()

    override suspend fun findAllPlayers(): List<StoredPlayerDto> {
        return players.map { it.value.toDto() }
    }

    override suspend fun findById(playerId: Long): StoredPlayerDto? {
        return players[playerId]?.toDto()
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

    override suspend fun addTransaction(playerId: Long, value: Double) {
        players[playerId]?.let { player ->
            player.cash += value
            transactions.add(TransactionRegister(player.id, value))
        }
    }

    override suspend fun findTransactionHistory(): List<StoredTransactionDto> {
        return transactions.map { transaction ->
            val player = players[transaction.playerId]
            StoredTransactionDto(transaction.value, player?.color ?: "")
        }
    }

    data class PlayerRegister(val id: Long, val color: String, var cash: Double) {

        fun toDto(): StoredPlayerDto {
            return StoredPlayerDto(id, color, cash)
        }
    }

    data class TransactionRegister(val playerId: Long, var value: Double)


}