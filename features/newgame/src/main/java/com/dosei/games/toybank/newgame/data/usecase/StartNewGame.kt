package com.dosei.games.toybank.newgame.data.usecase

import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.newgame.data.mapper.toNewEntity
import com.dosei.games.toybank.newgame.data.model.LeadPlayer
import javax.inject.Inject

val PLAYERS_RANGE = 2..6

class StartNewGame @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend operator fun invoke(
        leadPlayers: List<LeadPlayer>,
        initialBalance: Int
    ) {
        ensureValidBalance(initialBalance)
        ensureValidPlayers(leadPlayers)

        val players = leadPlayers.map { it.toNewEntity(initialBalance) }
        playerRepository.overridePlayerList(players)
        transactionRepository.clearHistory()
    }

    private fun ensureValidBalance(initialBalance: Int) {
        if (initialBalance <= 0) throw BusinessException(ErrorCode.INVALID_INITIAL_BALANCE)
    }

    private fun ensureValidPlayers(players: List<LeadPlayer>) {
        if (players.size !in PLAYERS_RANGE) throw BusinessException(ErrorCode.INVALID_PLAYER_AMOUNT)
    }
}