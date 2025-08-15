package com.dosei.games.toybank.home.presentation

import androidx.lifecycle.ViewModel
import com.dosei.games.toybank.core.data.model.GameState
import com.dosei.games.toybank.core.data.usecase.CheckGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val checkGameState: CheckGameState
): ViewModel() {

    fun isContinueEnabled() = checkGameState()
        .map { it is GameState.Ongoing }
}