package com.jgeniselli.banco.game.common.domain

import java.util.*

class MemoryPlayerRepository : PlayerRepository {

    private var autoIncrementPlayerId = 0
    private val players = mutableMapOf<Int, Player>()

    override fun createPlayer(color: Color): Player {
        val newPlayer = Player(
            id = autoIncrementPlayerId.inc(),
            color = color
        )
        players[newPlayer.id] = newPlayer
        return newPlayer
    }

    override fun findById(playerId: Int, onSuccess: (Player?) -> Unit) {
        onSuccess(players[playerId])
    }

    override fun findAll(onSuccess: (List<Player>) -> Unit, onError: () -> Unit) {
        onSuccess(players.map { it.value })
    }
}

class MemoryColorRepository : ColorRepository {

    private val colors = listOf<Color>(
        Color(id = 1, colorHex = "#42a5f5", name = "Azul"),
        Color(id = 2, colorHex = "#ffee58", name = "Amarelo"),
        Color(id = 3, colorHex = "#66bb6a", name = "Verde"),
        Color(id = 4, colorHex = "#ef5350", name = "Vermelho"),
        Color(id = 5, colorHex = "#ab47bc", name = "Roxo"),
        Color(id = 6, colorHex = "#bdbdbd", name = "Cinza")
    )

    override fun findAll(onSuccess: (List<Color>) -> Unit, onError: () -> Unit) {
        onSuccess(colors)
    }
}

class MemoryTransactionRepository : TransactionRepository {

    override fun saveTransaction(player: Player, value: Double) {

    }
}

class MemoryGameRepository : GameRepository {

    private var game: Game? = null

    override fun createAndActivateGame(selectedPlayers: List<Player>) {
        if (selectedPlayers.size < 2) throw InsufficientPlayersException()
        selectedPlayers.forEach { it.currentValue = 25000.00 }
        game = Game(
            id = 1,
            createTime = Date(),
            players = selectedPlayers
        )
    }

    override fun getActiveGame(): Game? = game
}