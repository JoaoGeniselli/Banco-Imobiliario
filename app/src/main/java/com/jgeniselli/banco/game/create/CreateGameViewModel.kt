package com.jgeniselli.banco.game.create

import androidx.lifecycle.*
import com.jgeniselli.banco.domain.*
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

internal class CreateGameViewModel(
    private val creditCardDataSource: CreditCardListDataSource,
    private val createGameUseCase: UseCase<List<CreditCard>, Game>
) : ViewModel(), LifecycleObserver {

    private val viewStateEvent = MutableLiveData<CreateGameViewState>()
    private lateinit var availableCards: List<CreditCard>

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        viewStateEvent.postValue(CreateGameViewState.LoadingStart)
        fetchAvailablePlayers()
    }

    private fun fetchAvailablePlayers() {
        creditCardDataSource.findAll(
            onSuccess = { cards ->
                availableCards = cards
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

    fun createGame(selectedIndexes: List<Int>) {
        val cards = selectedIndexes.map { availableCards[it] }
        createGameUseCase.execute(cards) { _, error ->
            viewStateEvent.value = if (error != null) {
                CreateGameViewState.Error
            } else {
                CreateGameViewState.RedirectToGame
            }
        }
    }

    fun observeViewState(owner: LifecycleOwner, observer: Observer<CreateGameViewState>) =
        viewStateEvent.observe(owner, observer)
}

