package com.dosei.games.toybank.core.data.model

import com.dosei.games.toybank.core.data.storage.player.Player

sealed interface GameState {
    data object NotStarted : GameState

    data class Ongoing(val players: List<Player>) : GameState

    data class GameOver(val winner: Player) : GameState
}