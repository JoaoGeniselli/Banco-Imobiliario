package com.jgeniselli.banco.core.usecase.game

import com.jgeniselli.banco.core.boundary.GameStorage
import com.jgeniselli.banco.core.boundary.NewPlayerStorage
import com.jgeniselli.banco.core.boundary.OperationStorage

class FinishGame(
    private val gameStorage: GameStorage,
    private val playerStorage: NewPlayerStorage,
    private val operationStorage: OperationStorage
) {
    suspend operator fun invoke() {
        gameStorage.clear()
        playerStorage.clear()
        operationStorage.clear()
    }
}