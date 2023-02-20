package com.jgeniselli.banco.core.usecase

import com.jgeniselli.banco.core.repository.GameStorage

class HasOngoingGame(
    private val gameStorage: GameStorage
) {

    suspend fun invoke(): Boolean = gameStorage.isOngoingGameAvailable()
}