package com.jgeniselli.banco.landing

import androidx.lifecycle.*

class MainViewModel : ViewModel(), LifecycleObserver {

    private val enableContinueButtonEvent = MutableLiveData<Unit>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun start() {
        // TODO: IMPLEMENT
//        repository.getActiveGame()?.let{
//            enableContinueButtonEvent.value = Unit
//        }
    }

    fun observeEnableContinueEvent(owner: LifecycleOwner, observer: Observer<Unit>) =
        enableContinueButtonEvent.observe(owner, observer)
}