package com.jgeniselli.banco.game.common.domain.db

import com.jgeniselli.banco.game.common.domain.CreditCard
import com.jgeniselli.banco.game.common.domain.Game
import com.jgeniselli.banco.game.common.domain.Player

class GameAPI(
    private val gameDao: DBGameDao,
    private val creditCardDao: DBCreditCardDao,
    private val playerDao: DBPlayerDao
) {

    fun findAvailableCreditCards(): List<CreditCard> {
        val dbCards = creditCardDao.findAll()
        return dbCards.map { CreditCard(it.id, it.colorHex, it.name) }
    }

    fun findAllPlayers(): List<Player> {
        val players = playerDao.findAll()
        return players.map { transformPlayer(it) }
    }

    private fun transformPlayer(player: DBPlayer): Player {
        val mappedCard = player.creditCard?.let { card ->
            CreditCard(card.id, card.colorHex, card.name)
        } ?: CreditCard.black()
        return Player(player.id, mappedCard, player.cash)
    }

    fun findCurrentGame() : Game? {
        val games = gameDao.findAllWithPlayers()
        return if (games.isEmpty()) {
            null
        } else {
            val currentGame = games.last()
            val mappedPlayers = currentGame.players.map { transformPlayer(it) }
            Game(currentGame.game.id, mappedPlayers)
        }
    }

    private fun mapGame(game: DBGameAndAllPlayers) {
        val mappedPlayers = game.players.map { transformPlayer(it) }
        Game(game.game.id, mappedPlayers)
    }
}