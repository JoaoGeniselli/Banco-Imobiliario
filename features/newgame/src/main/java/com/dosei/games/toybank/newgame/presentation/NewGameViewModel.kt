package com.dosei.games.toybank.newgame.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.dosei.games.toybank.core.base.StatefulViewModel
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.newgame.data.model.LeadPlayer
import com.dosei.games.toybank.newgame.data.usecase.StartNewGame
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class NewGameViewModel @Inject constructor(
    private val startNewGame: StartNewGame
) : StatefulViewModel<NewGameState>(initialState = NewGameState()) {

    fun onUpdateInitialBalance(newValue: Int) {
        updateState { it.copy(initialBalanceInCents = newValue) }
    }

    fun createPlayer(name: String, color: Color) {
        val newPlayer = LeadPlayer(name = name, colorARGB = color.toArgb())
        val updatedPlayers = state.value.players + newPlayer
        val updatedColors = state.value.availableColors - color
        updateState { it.copy(players = updatedPlayers, availableColors = updatedColors) }
    }

    fun removePlayer(player: LeadPlayer) {
        val updatedPlayers = state.value.players - player
        val updatedColors = state.value.availableColors + Color(player.colorARGB)
        updateState { it.copy(players = updatedPlayers, availableColors = updatedColors) }
    }

    fun onNewGameClick() {
        request {
            val snapshot = state.value
            startNewGame(snapshot.players, snapshot.initialBalanceInCents)
            sendEvent(NavigateTo(AppRoutes.Game.Play))
        }
    }
}