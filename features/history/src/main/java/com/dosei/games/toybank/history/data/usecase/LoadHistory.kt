package com.dosei.games.toybank.history.data.usecase

import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.history.data.mapper.toHistoryEntry
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class LoadHistory @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke() = combine(
        playerRepository.players,
        transactionRepository.history
    ) { players, history ->
        history.mapNotNull { it.toHistoryEntry(players) }
    }
}