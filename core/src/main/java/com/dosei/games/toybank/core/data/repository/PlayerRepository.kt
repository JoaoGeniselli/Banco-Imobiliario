package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    private val playerDao: PlayerDao,
) {
    val players = playerDao.fetchAllPlayers()

    suspend fun deposit(
        playerId: Int,
        amountInCents: Int
    ): Player {
        val player = fetchPlayer(playerId)
        val updatedPlayer = player.copy(balanceInCents = player.balanceInCents + amountInCents)
        playerDao.insert(updatedPlayer)
        return updatedPlayer
    }

    private suspend fun fetchPlayer(id: Int): Player =
        playerDao.fetchPlayerById(id)
            ?: throw BusinessException(ErrorCode.INVALID_PLAYER)

    suspend fun withdraw(
        playerId: Int,
        amountInCents: Int
    ): Player = deposit(playerId, amountInCents * -1)
}