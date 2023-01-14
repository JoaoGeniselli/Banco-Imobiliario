package com.jgeniselli.banco.home

import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.core.usecase.HasOngoingGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeViewModel(
    private val hasOngoingGame: HasOngoingGame = HasOngoingGame()
) : ViewModel() {

    val isGameAvailable: Flow<Boolean> = flow {
        emit(hasOngoingGame.invoke())
    }
}