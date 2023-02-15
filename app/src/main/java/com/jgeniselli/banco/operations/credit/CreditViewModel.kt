package com.jgeniselli.banco.operations.credit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.compose.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditViewModel(
    playerId: Int,
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val player = gameRepository.players.value.first { it.id == playerId }

    private val _state = MutableStateFlow(CreditState(player.balance))
    val state: StateFlow<CreditState> get() = _state

    fun updateValue(value: Double) {
        _state.update {
            it.copy(value = value, isDoneEnabled = value > 0.0)
        }
    }

    fun commitOperation() {
        viewModelScope.launch {
            gameRepository.credit(player.id, state.value.value)
            _state.update { it.copy(isOperationDone = true) }
        }
    }
}

data class CreditState(
    val balance: Double,
    val value: Double = 0.0,
    val isDoneEnabled: Boolean = false,
    val isOperationDone: Boolean = false
)