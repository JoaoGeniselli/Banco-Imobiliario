package com.jgeniselli.banco.game.transaction

import com.jgeniselli.banco.game.common.domain.GameRepository
import com.jgeniselli.banco.game.common.domain.Player

class CurrentGamePlayersDataSource(
    private val gameRepository: GameRepository
) : TransactionPlayersDataSource {

    override fun getPlayer(playerId: Long): Player {
        val game = gameRepository.getActiveGame()!!
        return game.players.first { it.id == playerId }
    }

    override fun getGamePlayersBut(playerId: Long): List<Player> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}