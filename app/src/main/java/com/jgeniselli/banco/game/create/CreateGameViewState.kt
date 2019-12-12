package com.jgeniselli.banco.game.create

import com.jgeniselli.banco.game.common.domain.Player

internal sealed class CreateGameViewState {
    object LoadingStart : CreateGameViewState()
    object LoadingStop : CreateGameViewState()
    class ContentFound(val players: List<Player>) : CreateGameViewState()
    object Error : CreateGameViewState()
    object RedirectToGame : CreateGameViewState()
}