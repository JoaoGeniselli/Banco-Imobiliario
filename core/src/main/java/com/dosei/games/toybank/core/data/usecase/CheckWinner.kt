package com.dosei.games.toybank.core.data.usecase

import com.dosei.games.toybank.core.data.storage.player.Player
import javax.inject.Inject

class CheckWinner @Inject constructor() {

    operator fun invoke(players: List<Player>) =
        players.filter { it.balanceInCents > 0 }
            .takeIf { it.size == 1 }
            ?.first()
}