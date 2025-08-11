package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import javax.inject.Inject

class GameSetupRepository @Inject constructor(
    private val playerDao: PlayerDao,
) {

    suspend fun hasOngoingGame(): Boolean = playerDao.fetchPlayersCount() > 0
}