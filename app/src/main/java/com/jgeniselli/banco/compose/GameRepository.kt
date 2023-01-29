package com.jgeniselli.banco.compose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

data class Player(
    val id: Int,
    val name: String,
    val balance: Double
)

interface GameRepository {
    val players: StateFlow<List<Player>>
    suspend fun credit(playerId: Int, value: Double)
    suspend fun debit(playerId: Int, value: Double)
    suspend fun transfer(sourceId: Int, recipientId: Int, value: Double)
}

class MemoryGameRepository : GameRepository {

    val gameId: Flow<Int> = flow {
        emit(1)
    }

    private val _players = MutableStateFlow(
        listOf(
            Player(1, "John", 1000.0),
            Player(2, "Peter", 1000.0),
            Player(3, "Alex", 1000.0),
        )
    )
    override val players: StateFlow<List<Player>> get() = _players

    override suspend fun credit(playerId: Int, value: Double) {
        val selectedPlayer = playerById(playerId)
        val updatedBalance = selectedPlayer.balance + value
        update(selectedPlayer.copy(balance = updatedBalance))
    }

    private fun playerById(id: Int) = _players.value.first { it.id == id }

    private fun update(player: Player) {
        val playerList = _players.value.toMutableList()
        val playerIndex = playerList.indexOfFirst { it.id == player.id }
        playerList[playerIndex] = player
        _players.value = playerList
    }

    override suspend fun debit(playerId: Int, value: Double) {
        val selectedPlayer = playerById(playerId)
        val updatedBalance = selectedPlayer.balance - value
        update(selectedPlayer.copy(balance = updatedBalance))
    }

    override suspend fun transfer(sourceId: Int, recipientId: Int, value: Double) {
        debit(sourceId, value)
        credit(recipientId, value)
    }
}

interface Storage {
    fun addCashToPlayer(playerId: Int, value: Double)
    fun removeCashToPlayer(playerId: Int, value: Double)
}

object MockStorage : Storage {


    override fun addCashToPlayer(playerId: Int, value: Double) {

    }

    override fun removeCashToPlayer(playerId: Int, value: Double) {

    }

}