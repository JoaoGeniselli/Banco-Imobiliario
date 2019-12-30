package com.jgeniselli.banco.game.create

import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

internal sealed class CreateGameViewState {
    object LoadingStart : CreateGameViewState()
    object LoadingStop : CreateGameViewState()
    class ContentFound(val rows: List<TitleAndColor>) : CreateGameViewState()
    object Error : CreateGameViewState()
    object RedirectToGame : CreateGameViewState()
}