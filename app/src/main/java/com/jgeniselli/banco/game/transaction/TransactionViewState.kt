package com.jgeniselli.banco.game.transaction

import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

sealed class TransactionViewState {
    class Content(val playerName: String?, val otherPlayerRows: List<TitleAndColor>) : TransactionViewState()
    object TransactionComplete : TransactionViewState()
}