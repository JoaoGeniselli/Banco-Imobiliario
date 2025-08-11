package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.mapper.toNewEntity
import com.dosei.games.toybank.core.data.model.LeadPlayer
import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import javax.inject.Inject

class GameSetupRepository @Inject constructor(
    private val playerDao: PlayerDao,
    private val transactionDao: TransactionDao
) {

    suspend fun setupNewGame(players: List<LeadPlayer>, initialBalanceInCents: Int) {
        if (initialBalanceInCents <= 0) throw BusinessException(ErrorCode.INVALID_INITIAL_BALANCE)

        val playerEntities = players.map { it.toNewEntity(initialBalanceInCents) }
        transactionDao.clearAll()
        playerDao.run {
            clearAll()
            insertAll(playerEntities)
        }
    }

    suspend fun hasOngoingGame(): Boolean = playerDao.fetchPlayersCount() > 0
}