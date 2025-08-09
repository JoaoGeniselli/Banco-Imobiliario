package com.dosei.games.toybank.data.repository

import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import com.dosei.games.toybank.data.mapper.toNewEntity
import com.dosei.games.toybank.data.model.LeadPlayer
import com.dosei.games.toybank.data.model.error.BusinessException
import com.dosei.games.toybank.data.model.error.ErrorCodes
import javax.inject.Inject

class GameSetupRepository @Inject constructor(
    private val playerDao: PlayerDao,
    private val transactionDao: TransactionDao
) {

    suspend fun setupNewGame(players: List<LeadPlayer>, initialBalanceInCents: Int) {
        if (initialBalanceInCents <= 0) throw BusinessException(ErrorCodes.INVALID_INITIAL_BALANCE)

        val playerEntities = players.map { it.toNewEntity(initialBalanceInCents) }
        transactionDao.clearAll()
        playerDao.run {
            clearAll()
            insertAll(playerEntities)
        }
    }

    suspend fun hasOngoingGame(): Boolean = playerDao.fetchPlayersCount() > 0
}