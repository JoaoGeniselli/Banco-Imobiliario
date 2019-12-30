package com.jgeniselli.banco.game.transaction

import com.jgeniselli.banco.game.common.domain.Player

interface TransactionPlayersDataSource {
    fun getPlayer(playerId: Long): Player
    fun getGamePlayersBut(playerId: Long): List<Player>
}