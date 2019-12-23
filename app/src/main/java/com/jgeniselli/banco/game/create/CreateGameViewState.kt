package com.jgeniselli.banco.game.create

import com.jgeniselli.banco.game.common.domain.Color

internal sealed class CreateGameViewState {
    object LoadingStart : CreateGameViewState()
    object LoadingStop : CreateGameViewState()
    class ContentFound(val colors: List<Color>) : CreateGameViewState()
    object Error : CreateGameViewState()
    object RedirectToGame : CreateGameViewState()
}