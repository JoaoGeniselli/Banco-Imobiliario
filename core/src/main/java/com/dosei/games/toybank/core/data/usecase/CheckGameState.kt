package com.dosei.games.toybank.core.data.usecase

import com.dosei.games.toybank.core.data.model.GameState
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckGameState @Inject constructor(
    private val repository: PlayerRepository,
    private val checkWinner: CheckWinner,
) {

    operator fun invoke() = repository.players.map { players ->
        if (players.isEmpty()) {
            GameState.NotStarted
        } else {
            val winner = checkWinner(players)
            if (winner != null) {
                GameState.GameOver(winner)
            } else {
                GameState.Ongoing(players)
            }
        }
    }
}