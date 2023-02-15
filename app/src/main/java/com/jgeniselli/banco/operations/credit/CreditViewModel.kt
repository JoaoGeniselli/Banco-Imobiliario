package com.jgeniselli.banco.operations.credit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.compose.GameRepository
import com.jgeniselli.banco.compose.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditViewModel(
    playerId: Int,
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        CreditState(player = gameRepository.players.value.first { it.id == playerId })
    )
    val state: StateFlow<CreditState> get() = _state

    fun updateValue(value: Double) {
        _state.update {
            it.copy(value = value, isDoneEnabled = value in 0.01 .. it.player.balance)
        }
    }

    fun commitOperation() {
        viewModelScope.launch {
            with(state.value) {
                gameRepository.credit(player.id, value)
                _state.update { it.copy(isOperationDone = true) }
            }
        }
    }
}

data class CreditState(
    val player: Player,
    val value: Double = 0.0,
    val isDoneEnabled: Boolean = false,
    val isOperationDone: Boolean = false
)