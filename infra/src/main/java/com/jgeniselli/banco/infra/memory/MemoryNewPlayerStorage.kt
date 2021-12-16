package com.jgeniselli.banco.infra.memory

import com.jgeniselli.banco.core.Player
import com.jgeniselli.banco.core.boundary.NewPlayerStorage

class MemoryNewPlayerStorage : NewPlayerStorage {

    private var currentAvailableId = 0
    private var players = listOf<Player>()

    override suspend fun createPlayers(initialValue: Long, players: List<String>) {
        this.players = players.map { name ->
            currentAvailableId += 1
            Player(
                id = currentAvailableId,
                name = name,
                balance = initialValue
            )
        }.sortedBy { it.name }
    }

    override suspend fun findAllPlayers(): List<Player> {
        return players.toList()
    }

    override suspend fun findPlayerById(id: Int): Player? {
        return players.firstOrNull { it.id == id }
    }

    override suspend fun updateBalance(playerId: Int, balance: Long) {
        val player = findPlayerById(playerId) ?: return
        val updatedPlayer = player.copy(balance = balance)
        replacePlayer(updatedPlayer)
    }

    private fun replacePlayer(player: Player) {
        val placeholder = players.toMutableList()
        placeholder.removeIf { it.id == player.id }
        placeholder.add(player)
        players = placeholder.sortedBy { it.name }
    }

    override suspend fun clear() {
        players = listOf()
    }
}