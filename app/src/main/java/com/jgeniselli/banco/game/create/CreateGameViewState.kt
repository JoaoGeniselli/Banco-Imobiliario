package com.jgeniselli.banco.game.create

import com.jgeniselli.banco.game.common.domain.CreditCard

internal sealed class CreateGameViewState {
    object LoadingStart : CreateGameViewState()
    object LoadingStop : CreateGameViewState()
    class ContentFound(val creditCards: List<CreditCard>) : CreateGameViewState()
    object Error : CreateGameViewState()
    object RedirectToGame : CreateGameViewState()
}