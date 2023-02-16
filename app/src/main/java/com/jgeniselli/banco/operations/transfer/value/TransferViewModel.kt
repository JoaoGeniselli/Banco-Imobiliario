package com.jgeniselli.banco.operations.transfer.value

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.compose.GameRepository
import com.jgeniselli.banco.compose.Player
import com.jgeniselli.banco.ui.component.PlayerSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransferViewModel(
    private val playerId: Int,
    private val gameRepository: GameRepository
) : ViewModel() {

    private val sourcePlayer = gameRepository.players.value.first { it.id == playerId }
    private val selectedPlayerIndex = MutableStateFlow<Int?>(null)
    private val selectedValue = MutableStateFlow(0.0)
    private val isOperationDone = MutableStateFlow(false)
    private val availableRecipients = gameRepository.players.value.filter { it.id != playerId }
    private val recipient get() = selectedPlayerIndex.value?.let { availableRecipients[it] }

    val uiState = combine(
        selectedPlayerIndex,
        selectedValue,
        isOperationDone
    ) { selectedIndex, value, isOperationDone ->
        TransferState(
            balance = sourcePlayer.balance,
            isOperationDone = isOperationDone,
            transferValue = value,
            isDoneAvailable = value > 0.0 && selectedIndex != null,
            availableRecipients = availableRecipients.mapIndexed { index, player ->
                player.toSummary(index == selectedIndex)
            }
        )
    }

    fun updateValue(value: Double) {
        selectedValue.value = value
    }

    fun selectRecipientAt(index: Int) {
        selectedPlayerIndex.value = index
    }

    fun onDone() {
        val validRecipient = recipient ?: return
        viewModelScope.launch {
            gameRepository.transfer(sourcePlayer.id, validRecipient.id, selectedValue.value)
            isOperationDone.update { true }
        }
    }

    private fun Player.toSummary(isSelected: Boolean): PlayerSummary {
        return PlayerSummary(name, Color(color), isSelected)
    }
}