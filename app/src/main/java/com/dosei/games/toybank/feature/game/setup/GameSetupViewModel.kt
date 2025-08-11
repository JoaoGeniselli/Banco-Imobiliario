package com.dosei.games.toybank.feature.game.setup

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.core.data.model.LeadPlayer
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.UiError
import com.dosei.games.toybank.core.data.model.UiEvent
import com.dosei.games.toybank.core.data.repository.GameSetupRepository
import com.dosei.games.toybank.core.navigation.AppRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSetupViewModel @Inject constructor(
    private val repository: GameSetupRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GameSetupState())
    val state = _state.asStateFlow()

    private val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()

    fun createPlayer(name: String, color: Color) {
        val newPlayer = LeadPlayer(name = name, colorARGB = color.toArgb())
        val updatedPlayers = _state.value.players + newPlayer
        val updatedColors = _state.value.availableColors - color
        _state.update { it.copy(players = updatedPlayers, availableColors = updatedColors) }
    }

    fun removePlayer(player: LeadPlayer) {
        val updatedPlayers = _state.value.players - player
        val updatedColors = _state.value.availableColors + Color(player.colorARGB)
        _state.update { it.copy(players = updatedPlayers, availableColors = updatedColors) }
    }

    fun onNewGameClick() {
        viewModelScope.launch {
            val snapshot = _state.value
            runCatching {
                startLoading()
                repository.setupNewGame(snapshot.players, snapshot.initialBalanceInCents)
            }.onSuccess {
                stopLoading()
                _events.send(NavigateTo(AppRoutes.Game.Play))
            }.onFailure { failure ->
                stopLoading()
                _events.send(UiError(failure))
            }
        }
    }

    private fun startLoading() = _state.update { it.copy(isLoading = true) }

    private fun stopLoading() = _state.update { it.copy(isLoading = false) }
}