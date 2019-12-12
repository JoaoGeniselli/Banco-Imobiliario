package com.jgeniselli.banco.game.play

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.Game
import com.jgeniselli.banco.game.common.domain.GameRepository

class GameViewModel(
    private val gameRepository: GameRepository
) : ViewModel(), LifecycleObserver {

    private lateinit var currentGame: Game
    private val viewStateEvent = MutableLiveData<GameViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        gameRepository.getActiveGame()?.let {
            currentGame = it
        } ?: run {
            viewStateEvent.postValue(GameViewState.Error)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun refresh() {
        viewStateEvent.postValue(GameViewState.PlayersFound(currentGame.players))
    }

    fun observeViewState(owner: LifecycleOwner, observer: Observer<GameViewState>) =
        viewStateEvent.observe(owner, observer)
}