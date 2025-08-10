package com.dosei.games.toybank.transaction.data.usecase

import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCodes
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.transaction.TransactionState
import javax.inject.Inject

class PerformTransaction @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(state: TransactionState) {
        if (state.amountInCents <= 0) throw BusinessException(ErrorCodes.INVALID_INITIAL_BALANCE)
        when (state.type) {
            TransactionType.DEPOSIT -> repository.deposit(
                playerId = state.playerId,
                amountInCents = state.amountInCents
            )

            TransactionType.WITHDRAW -> repository.withdraw(
                playerId = state.playerId,
                amountInCents = state.amountInCents
            )

            TransactionType.TRANSFER -> {
                if (state.destinationPlayerId == null) {
                    throw BusinessException(ErrorCodes.INVALID_DESTINATION_PLAYER)
                }
                repository.withdraw(
                    playerId = state.playerId,
                    amountInCents = state.amountInCents
                )
                repository.deposit(
                    playerId = state.destinationPlayerId,
                    amountInCents = state.amountInCents
                )
            }
        }
    }
}