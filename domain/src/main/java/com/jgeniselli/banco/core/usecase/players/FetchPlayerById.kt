package com.jgeniselli.banco.core.usecase.players

import com.jgeniselli.banco.core.ErrorCode
import com.jgeniselli.banco.core.GameException
import com.jgeniselli.banco.core.Player
import com.jgeniselli.banco.core.boundary.NewPlayerStorage

class FetchPlayerById(
    private val storage: NewPlayerStorage
) {
    suspend operator fun invoke(id: Int): Player =
        storage.findPlayerById(id) ?: throw GameException(ErrorCode.UNKNOWN_PLAYER)
}