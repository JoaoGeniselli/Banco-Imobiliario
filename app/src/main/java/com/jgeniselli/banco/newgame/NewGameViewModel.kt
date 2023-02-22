package com.jgeniselli.banco.newgame

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.compose.ui.theme.*
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.ui.component.PlayerSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class NewGameViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val availableColors = LinkedList<Color>().apply {
        add(PlayerRed)
        add(PlayerBlue)
        add(PlayerGreen)
        add(PlayerYellow)
        add(PlayerGray)
        add(PlayerPurple)
    }

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> get() = _uiState

    fun onAddNewPlayer(name: String) {
        _uiState.update { old ->
            val players = old.players + PlayerSummary(
                name,
                availableColors.pop()
            )
            old.copy(
                players = players,
                canAddNewPlayer = players.size < MAX_PLAYERS,
                isGameStartAllowed = players.size >= MIN_PLAYERS,
            )
        }

    }

    fun onStartGame() {
        viewModelScope.launch {
            gameRepository.startGame(_uiState.value.players.map { it.name to it.color.value })
            _uiState.update { it.copy(isGameStarted = true) }
        }
    }

    companion object {
        private const val MAX_PLAYERS = 6
        private const val MIN_PLAYERS = 2
    }
}