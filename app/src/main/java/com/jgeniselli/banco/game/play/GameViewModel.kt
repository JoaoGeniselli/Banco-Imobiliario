package com.jgeniselli.banco.game.play

import androidx.lifecycle.*
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.StoredPlayerDto
import kotlinx.coroutines.*
import java.text.NumberFormat

class GameViewModel(
    private val api: GameAPI,
    private val currencyFormatter: NumberFormat
) : ViewModel(), LifecycleObserver {

    private val viewStateEvent = MutableLiveData<GameViewState>()

    val viewState: LiveData<GameViewState>
        get() = viewStateEvent

    private lateinit var players: List<StoredPlayerDto>

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun refresh() {
        GlobalScope.launch {
            players = api.getPlayers()
            val playerRows = mapPlayersToRows()
            viewStateEvent.postValue(GameViewState.PlayersFound(playerRows))
        }
    }

    private fun mapPlayersToRows() = players.map {
        PlayerRow(it.colorHex, currencyFormatter.format(it.currentCash))
    }

    fun onPlayerSelected(position: Int) {
        val playerId = players[position].id
        viewStateEvent.postValue(GameViewState.RedirectToTransaction(playerId))
    }
}