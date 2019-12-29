package com.jgeniselli.banco.game.transaction

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.GameRepository
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.domain.PlayerRepository
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

class TransactionViewModel(
    private val playerId: Long,
    private val playerRepository: PlayerRepository,
    private val gameRepository: GameRepository,
    private val transactionService: Someone,
    private val someone: Someone2
) : ViewModel(), LifecycleObserver {

    interface Someone {
        fun debit(playerId: Long, value: Double)
        fun credit(playerId: Long, value: Double)
    }

    interface Someone2 {
        fun getPlayer(playerId: Long): Player
        fun getGamePlayersBut(playerId: Long): List<Player>

    }

    private lateinit var selectedPlayer: Player
    private lateinit var otherPlayers: List<Player>
    private val viewStateEvent = MutableLiveData<TransactionViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        selectedPlayer = someone.getPlayer(playerId)
        otherPlayers = someone.getGamePlayersBut(playerId)
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