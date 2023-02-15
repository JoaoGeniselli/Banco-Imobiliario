package com.jgeniselli.banco.newgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.R
import com.jgeniselli.banco.compose.GameRepository
import com.jgeniselli.banco.ui.component.PlayerSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewGameViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> get() = _uiState

    fun onAddNewPlayer(name: String) {
        _uiState.update { old ->
            val players = old.players + PlayerSummary(name, R.drawable.ic_baseline_pets_24)
            old.copy(
                players = players,
                canAddNewPlayer = players.size < 6,
                isGameStartAllowed = players.size >= 2,
            )
        }

    }

    fun onStartGame() {
        viewModelScope.launch {
            gameRepository.startGame(_uiState.value.players.map { it.name })
            _uiState.update { it.copy(isGameStarted = true) }
        }
    }
}