package com.jgeniselli.banco.game.transaction

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

class TransactionViewModel(
    private val playerId: Long,
    private val transactionService: TransactionService,
    private val playerDataSource: TransactionPlayersDataSource
) : ViewModel(), LifecycleObserver {

    private lateinit var selectedPlayer: Player
    private lateinit var otherPlayers: List<Player>
    private val viewStateEvent = MutableLiveData<TransactionViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        selectedPlayer = playerDataSource.getPlayer(playerId)
        otherPlayers = playerDataSource.getGamePlayersBut(playerId)
        viewStateEvent.value = TransactionViewState.Content(
            selectedPlayer.creditCard.name,
            otherPlayers.map { TitleAndColor(it.creditCard.name, it.creditCard.colorHex) }
        )
    }

    fun observeViewState(owner: LifecycleOwner, observer: Observer<TransactionViewState>) {
        viewStateEvent.observe(owner, observer)
    }

    fun applyDebit(value: Double) {
        transactionService.debit(playerId, value)
        viewStateEvent.postValue(TransactionViewState.TransactionComplete)
    }

    fun applyCredit(value: Double) {
        transactionService.credit(playerId, value)
        viewStateEvent.postValue(TransactionViewState.TransactionComplete)
    }

    fun applyTransfer(value: Double, selectedIndex: Int) {
        val destinationId = otherPlayers[selectedIndex].id
        transactionService.debit(playerId, value)
        transactionService.credit(destinationId, value)
        viewStateEvent.postValue(TransactionViewState.TransactionComplete)
    }
}