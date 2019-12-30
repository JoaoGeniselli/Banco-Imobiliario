package com.jgeniselli.banco.game.common.domain

class MemoryPlayerRepository : PlayerRepository {

    private var autoIncrementPlayerId = 0L
    private val players = mutableMapOf<Long, Player>()

    override fun createPlayer(creditCard: CreditCard): Player {
        val newPlayer = Player(
            id = autoIncrementPlayerId.inc(),
            creditCard = creditCard
        )
        players[newPlayer.id] = newPlayer
        return newPlayer
    }

    override fun findById(playerId: Long, onSuccess: (Player?) -> Unit) {
        onSuccess(players[playerId])
    }

    override fun findAll(onSuccess: (List<Player>) -> Unit, onError: () -> Unit) {
        onSuccess(players.map { it.value })
    }
}

class MemoryColorRepository : CreditCardRepository {

    private val colors = listOf<CreditCard>(
        CreditCard(id = 1, colorHex = "#42a5f5", name = "Azul"),
        CreditCard(id = 2, colorHex = "#ffee58", name = "Amarelo"),
        CreditCard(id = 3, colorHex = "#66bb6a", name = "Verde"),
        CreditCard(id = 4, colorHex = "#ef5350", name = "Vermelho"),
        CreditCard(id = 5, colorHex = "#ab47bc", name = "Roxo"),
        CreditCard(id = 6, colorHex = "#bdbdbd", name = "Cinza")
    )

    override fun findAll(onSuccess: (List<CreditCard>) -> Unit, onError: () -> Unit) {
        onSuccess(colors)
    }
}

class MemoryGameRepository : GameRepository {

    private var game: Game? = null

    override fun createAndActivateGame(selectedPlayers: List<Player>) {
        if (selectedPlayers.size < 2) throw InsufficientPlayersException()
        selectedPlayers.forEach { it.currentValue = 25000.00 }
        game = Game(
            id = 1,
            players = selectedPlayers
        )
    }

    override fun getActiveGame(): Game? = game
}