package com.jgeniselli.banco.game.common.domain

class MemoryPlayerRepository : PlayerRepository {

    private var autoIncrementPlayerId = 0
    private val players = mutableMapOf<Int, Player>()

    override fun createPlayer(color: Color): Player {
        val newPlayer = MemoryPlayer(id = autoIncrementPlayerId.inc(), colorHex = color.colorHex, name = color.name)
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
        MemoryColor(id = 1, colorHex = "#42a5f5", name = "Azul"),
        MemoryColor(id = 2, colorHex = "#ffee58", name = "Amarelo"),
        MemoryColor(id = 3, colorHex = "#66bb6a", name = "Verde"),
        MemoryColor(id = 4, colorHex = "#ef5350", name = "Vermelho"),
        MemoryColor(id = 5, colorHex = "#ab47bc", name = "Roxo"),
        MemoryColor(id = 6, colorHex = "#bdbdbd", name = "Cinza")
    )

    override fun findAll(onSuccess: (List<Color>) -> Unit, onError: () -> Unit) {
        onSuccess(colors)
    }
}

data class MemoryColor(
    override val id: Int,
    override val colorHex: String,
    override val name: String
) : Color

class MemoryTransactionRepository : TransactionRepository {

    override fun saveTransaction(player: Player, value: Double) {

    }
}

class MemoryGameRepository : GameRepository {

    private var game: MemoryGame? = null

    override fun createAndActivateGame(selectedPlayers: List<Player>) {
        if (selectedPlayers.size < 2) throw InsufficientPlayersException()
        selectedPlayers.forEach { it.currentValue = 25000.00 }
        game = MemoryGame(selectedPlayers)
    }

    override fun getActiveGame(): Game? = game
}

private class MemoryGame(
    override val players: List<Player>,
    override val transactions: List<Transaction> = listOf(),
    override val id: Int = 0
) : Game

class MemoryPlayer(
    override val id: Int,
    override val colorHex: String = "#F00",
    override var currentValue: Double = 25000.00,
    override val name: String = "Player"
) : Player