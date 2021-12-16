package com.jgeniselli.banco.core.usecase.players

import com.jgeniselli.banco.core.ErrorCode
import com.jgeniselli.banco.core.GameException
import com.jgeniselli.banco.core.Player
import com.jgeniselli.banco.core.boundary.NewPlayerStorage

class FetchPlayers(
    private val storage: NewPlayerStorage
) {
    suspend operator fun invoke(): List<Player> {
        val players = storage.findAllPlayers()
        if (players.isEmpty()) throw GameException(ErrorCode.UNAVAILABLE_GAME_SESSION)
        return players
    }
}