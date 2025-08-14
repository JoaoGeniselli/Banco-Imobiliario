package com.dosei.games.toybank.gameplay.presentation.play

import androidx.lifecycle.ViewModel
import com.dosei.games.toybank.core.data.model.GameState
import com.dosei.games.toybank.core.data.usecase.CheckGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class GameplayViewModel @Inject constructor(
    private val checkGameState: CheckGameState
) : ViewModel() {

    private val gameState by lazy { checkGameState() }

    fun fetchPlayers() = gameState.filterIsInstance<GameState.Ongoing>().map { it.players }

    fun observeWinner() = gameState.filterIsInstance<GameState.GameOver>().map { it.winner }
}