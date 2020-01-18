package com.jgeniselli.banco.game.transaction

import androidx.lifecycle.*
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.StoredPlayerDto
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val selectedPlayerId: Long,
    private val gameAPI: GameAPI
) : ViewModel(), LifecycleObserver {

    private lateinit var otherPlayers: List<StoredPlayerDto>
    private val viewStateEvent = MutableLiveData<TransactionViewState>()

    val viewState: LiveData<TransactionViewState>
        get() = viewStateEvent

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        GlobalScope.launch {
            otherPlayers = gameAPI.getPlayers().filter { it.id != selectedPlayerId }

            val otherPlayerRows = otherPlayers
                .map { TitleAndColor("Jogador", it.colorHex) }

            viewStateEvent.postValue(
                TransactionViewState.Content(
                    "Jogador Selecionado",
                    otherPlayerRows
                )
            )
        }
    }

    fun applyDebit(value: Double) {
        GlobalScope.launch {
            gameAPI.debit(selectedPlayerId, value)
            finishTransaction()
        }
    }

    fun applyCredit(value: Double) {
        GlobalScope.launch {
            gameAPI.credit(selectedPlayerId, value)
            finishTransaction()
        }
    }

    fun applyTransfer(value: Double, selectedIndex: Int) {
        GlobalScope.launch {
            val destinationPlayerId = otherPlayers[selectedIndex].id
            gameAPI.transfer(selectedPlayerId, destinationPlayerId, value)
            finishTransaction()
        }
    }

    private fun finishTransaction() {
        viewStateEvent.postValue(TransactionViewState.TransactionComplete)
    }
}