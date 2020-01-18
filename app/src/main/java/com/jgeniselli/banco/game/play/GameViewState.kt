package com.jgeniselli.banco.game.play

sealed class GameViewState {

    data class PlayersFound(val players: List<PlayerRow>) : GameViewState()

    data class Error(val error: String?) : GameViewState()

    data class RedirectToTransaction(val playerId: Long) : GameViewState()
}