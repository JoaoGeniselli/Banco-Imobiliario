package com.jgeniselli.banco.game.play

import androidx.lifecycle.*
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.dto.StoredPlayerDto
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
        refreshPlayers()
    }

    private fun refreshPlayers() {
        api.getPlayers { storedPlayers ->
            players = storedPlayers
            val playerRows = players.map { it.toRow() }
            viewStateEvent.postValue(GameViewState.PlayersFound(playerRows))
        }
    }

    private fun StoredPlayerDto.toRow() = PlayerRow(colorHex, currencyFormatter.format(currentCash))

    fun onPlayerSelected(position: Int) {
        val playerId = players[position].id
        viewStateEvent.postValue(GameViewState.RedirectToTransaction(playerId))
    }

    fun onResetRequested() {
        api.resetGame {
            refreshPlayers()
        }
    }
}