package com.jgeniselli.banco.core.usecase.game

import com.jgeniselli.banco.core.Player
import com.jgeniselli.banco.core.usecase.players.FetchPlayers

class CheckWinner(
    private val fetchPlayers: FetchPlayers
) {
    suspend operator fun invoke(): Player? =
        fetchPlayers()
            .filter { player -> player.balance > 0 }
            .takeIf { positivePlayers -> positivePlayers.size == ONLY_ONE }
            ?.first()

    companion object {
        private const val ONLY_ONE = 1
    }
}