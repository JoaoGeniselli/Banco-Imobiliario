package com.jgeniselli.banco.game.transaction

import com.jgeniselli.banco.game.common.domain.Player

sealed class TransactionViewState {
    object Loading : TransactionViewState()
    object TransactionComplete : TransactionViewState()
    class Content(val playerName: String?, val players: List<Player>) : TransactionViewState()
}