package com.jgeniselli.banco.operations.debit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.compose.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.max

class DebitViewModel(
    playerId: Int,
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val player = gameRepository.players.value.first { it.id == playerId }

    private val _state = MutableStateFlow(DebitState(player.balance))
    val state: StateFlow<DebitState> get() = _state

    fun updateValue(value: Double) {
        _state.update {
            it.copy(value = value, isDoneEnabled = value > 0.0)
        }
    }

    fun onShortcut(value: Double) {
        _state.update { old ->
            val updatedValue = max(old.value + value, 0.0)
            old.copy(value = updatedValue, isDoneEnabled = updatedValue > 0.0)
        }
    }

    fun commitOperation() {
        viewModelScope.launch {
            gameRepository.debit(player.id, state.value.value)
            _state.update { it.copy(isOperationDone = true) }
        }
    }
}