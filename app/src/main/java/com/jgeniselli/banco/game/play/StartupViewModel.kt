package com.jgeniselli.banco.game.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.core.usecase.game.IsGameOngoing
import kotlinx.coroutines.launch

class StartupViewModel(
    private val isGameOngoing: IsGameOngoing
) : ViewModel() {

    private val _initGameCreation = MutableLiveData<Unit>()
    val initGameCreation: LiveData<Unit> get() = _initGameCreation

    private val _redirectToOngoingGame = MutableLiveData<Unit>()
    val redirectToOngoingGame: LiveData<Unit> get() = _redirectToOngoingGame

    fun start() {
        viewModelScope.launch {
            if (isGameOngoing()) {
                _redirectToOngoingGame.value = Unit
            } else {
                _initGameCreation.value = Unit
            }
        }
    }
}