package com.dosei.games.toybank.feature.game.setup

import androidx.compose.animation.core.snap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.dosei.games.toybank.AppRoutes
import com.dosei.games.toybank.data.model.LeadPlayer
import com.dosei.games.toybank.data.model.Navigate
import com.dosei.games.toybank.data.model.UiError
import com.dosei.games.toybank.data.model.UiEvent
import com.dosei.games.toybank.data.repository.GameSetupRepository
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

    fun updateDefaultBalance(balanceInCents: Int) {
        _state.update { it.copy(initialBalanceInCents = balanceInCents) }
    }

    fun createPlayer(name: String, color: Int) {
        val newPlayer = LeadPlayer(name = name, colorARGB = color)
        val updatedPlayers = _state.value.players + newPlayer
        _state.update { it.copy(players = updatedPlayers) }
    }

    fun removePlayer(player: LeadPlayer) {
        val updatedPlayers = _state.value.players - player
        _state.update { it.copy(players = updatedPlayers) }
    }

    fun onNewGameClick() {
        viewModelScope.launch {
            val snapshot = _state.value
            runCatching {
                startLoading()
                repository.setupNewGame(snapshot.players, snapshot.initialBalanceInCents)
            }.onSuccess {
                stopLoading()
                _events.send(Navigate(AppRoutes.Gameplay))
            }.onFailure { failure ->
                stopLoading()
                _events.send(UiError(failure))
            }
        }
    }

    private fun startLoading() = _state.update { it.copy(isLoading = true) }

    private fun stopLoading() = _state.update { it.copy(isLoading = false) }
}