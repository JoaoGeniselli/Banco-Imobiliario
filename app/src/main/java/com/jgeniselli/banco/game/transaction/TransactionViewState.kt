package com.jgeniselli.banco.game.transaction

import com.jgeniselli.banco.game.common.domain.Player

sealed class TransactionViewState {
    class Content(val playerName: String?, val players: List<Player>) : TransactionViewState()
    object TransactionComplete : TransactionViewState()
}