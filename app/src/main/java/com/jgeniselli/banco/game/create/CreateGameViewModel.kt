package com.jgeniselli.banco.game.create

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.CreditCard
import com.jgeniselli.banco.game.common.domain.ColorRepository
import com.jgeniselli.banco.game.common.domain.GameRepository
import com.jgeniselli.banco.game.common.domain.PlayerRepository
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

internal class CreateGameViewModel(
    private val colorRepository: ColorRepository,
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
        colorRepository.findAll(
            onSuccess = { cards ->
                val rows = cards.map { TitleAndColor(it.name, it.colorHex) }
                viewStateEvent.postValue(CreateGameViewState.LoadingStop)
                viewStateEvent.postValue(CreateGameViewState.ContentFound(rows))
            },
            onError = {
                viewStateEvent.postValue(CreateGameViewState.LoadingStop)
                viewStateEvent.postValue(CreateGameViewState.Error)
            }
        )
    }

    fun createGame(selectedCreditCards: List<CreditCard>) {
        val players = selectedCreditCards.map { color ->
            playerRepository.createPlayer(color)
        }
        gameRepository.createAndActivateGame(players)
        viewStateEvent.value = CreateGameViewState.RedirectToGame
    }

    fun observeViewState(owner: LifecycleOwner, observer: Observer<CreateGameViewState>) =
        viewStateEvent.observe(owner, observer)
}

