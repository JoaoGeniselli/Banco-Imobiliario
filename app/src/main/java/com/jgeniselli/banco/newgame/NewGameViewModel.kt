package com.jgeniselli.banco.newgame

import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.R
import com.jgeniselli.banco.compose.PlayerSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class NewGameViewModel : ViewModel() {

    private val _players = MutableStateFlow<List<PlayerSummary>>(listOf())
    private val _isStartAllowed = MutableStateFlow(false)
    private val _canAddPlayer = MutableStateFlow(true)

    val uiState = combine(_players, _isStartAllowed, _canAddPlayer) { players, canStart, canAdd ->
        NewGameUiState(
            players = players,
            isGameStartAllowed = canStart,
            canAddNewPlayer = canAdd
        )
    }

    fun onAddNewPlayer(name: String) {
        _players.value = _players.value + PlayerSummary(name, R.drawable.ic_baseline_pets_24)
        _canAddPlayer.value = _players.value.size < 6
        _isStartAllowed.value = _players.value.size > 1
    }
}