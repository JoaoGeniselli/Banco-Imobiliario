package com.jgeniselli.banco.game.common.domain

class MemoryPlayerRepository : PlayerRepository {

    override fun findById(playerId: Int, onSuccess: (Player?) -> Unit) {
        onSuccess(players[playerId])
    }

    override fun findAll(onSuccess: (List<Player>) -> Unit, onError: () -> Unit) {
        onSuccess(players.map { it.value })
    }

    private val players = mapOf(
        1 to MemoryPlayer(id = 1, colorHex = "#42a5f5", name = "Azul"),
        2 to MemoryPlayer(id = 2, colorHex = "#ffee58", name = "Amarelo"),
        3 to MemoryPlayer(id = 3, colorHex = "#66bb6a", name = "Verde"),
        4 to MemoryPlayer(id = 4, colorHex = "#ef5350", name = "Vermelho"),
        5 to MemoryPlayer(id = 5, colorHex = "#ab47bc", name = "Roxo"),
        6 to MemoryPlayer(id = 6, colorHex = "#bdbdbd", name = "Cinza")
    )
}

class MemoryTransactionRepository : TransactionRepository {

    override fun saveTransaction(player: Player, value: Double) {

    }
}

class MemoryGameRepository : GameRepository {

    private var game: MemoryGame? = null

    override fun createAndActivateGame(selectedPlayers: List<Player>) {
        game = MemoryGame(selectedPlayers)
    }

    override fun getActiveGame(): Game? = game
}

private class MemoryGame(
    override val players: List<Player>,
    override val transactions: List<Transaction> = listOf()
) : Game

class MemoryPlayer(
    override val id: Int,
    override val colorHex: String = "#F00",
    override var currentValue: Double = 25000.00,
    override val name: String = "Player"
) : Player