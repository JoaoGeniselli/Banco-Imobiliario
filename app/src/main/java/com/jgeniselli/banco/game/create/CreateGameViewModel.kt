package com.jgeniselli.banco.game.create

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.GameRepository
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.domain.PlayerRepository

internal class CreateGameViewModel(
    private val playerRepository: PlayerRepository,
    private val gameRepository: GameRepository
) : ViewModel(), LifecycleObserver {

    private val viewStateEvent = MutableLiveData<CreateGameViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        viewStateEvent.postValue(CreateGameViewState.LoadingStart)
        fetchAvailablePlayers()
    }

    private fun fetchAvailablePlayers() {
        playerRepository.findAll(
            onSuccess = {
                viewStateEvent.postValue(CreateGameViewState.LoadingStop)
                viewStateEvent.postValue(CreateGameViewState.ContentFound(it))
            },
            onError = {
                viewStateEvent.postValue(CreateGameViewState.LoadingStop)
                viewStateEvent.postValue(CreateGameViewState.Error)
            }
        )
    }

    fun createGame(selectedPlayers: List<Player>) {
        gameRepository.createAndActivateGame(selectedPlayers)
        viewStateEvent.value = CreateGameViewState.RedirectToGame
    }

    fun observeViewState(owner: LifecycleOwner, observer: Observer<CreateGameViewState>) =
        viewStateEvent.observe(owner, observer)
}

