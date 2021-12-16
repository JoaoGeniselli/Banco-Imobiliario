package com.jgeniselli.banco.core.usecase.game

import com.jgeniselli.banco.core.boundary.NewPlayerStorage

class IsGameOngoing(
    private val storage: NewPlayerStorage
) {
    suspend operator fun invoke(): Boolean =
        storage.findAllPlayers().isNotEmpty()
}