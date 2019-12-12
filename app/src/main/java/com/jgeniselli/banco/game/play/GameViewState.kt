package com.jgeniselli.banco.game.play

import com.jgeniselli.banco.game.common.domain.Player

sealed class GameViewState {

    class PlayersFound(val players: List<Player>) : GameViewState()
    object Error : GameViewState()
}