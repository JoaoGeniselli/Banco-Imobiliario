package com.dosei.games.toybank.transaction.data.usecase

import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.transaction.TransactionState
import com.dosei.games.toybank.transaction.data.mapper.toDatabaseEntity
import javax.inject.Inject

class PerformTransaction @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(state: TransactionState) {
        if (state.amountInCents <= 0) throw BusinessException(ErrorCode.INVALID_AMOUNT)
        when (state.type) {
            TransactionType.DEPOSIT -> playerRepository.deposit(
                playerId = state.playerId,
                amountInCents = state.amountInCents
            )

            TransactionType.WITHDRAW -> playerRepository.withdraw(
                playerId = state.playerId,
                amountInCents = state.amountInCents
            )

            TransactionType.TRANSFER -> {
                if (state.destinationPlayerId == null) {
                    throw BusinessException(ErrorCode.INVALID_DESTINATION_PLAYER)
                }
                playerRepository.withdraw(
                    playerId = state.playerId,
                    amountInCents = state.amountInCents
                )
                playerRepository.deposit(
                    playerId = state.destinationPlayerId,
                    amountInCents = state.amountInCents
                )
            }
        }
        val historyEntity = state.toDatabaseEntity()
        transactionRepository.addToHistory(historyEntity)
    }
}