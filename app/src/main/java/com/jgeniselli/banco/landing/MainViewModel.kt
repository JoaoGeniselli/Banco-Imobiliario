package com.jgeniselli.banco.landing

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.GameRepository

class MainViewModel(private val repository: GameRepository) : ViewModel(), LifecycleObserver {

    private val enableContinueButtonEvent = MutableLiveData<Unit>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun start() {
        repository.getActiveGame()?.let{
            enableContinueButtonEvent.value = Unit
        }
    }

    fun observeEnableContinueEvent(owner: LifecycleOwner, observer: Observer<Unit>) =
        enableContinueButtonEvent.observe(owner, observer)
}