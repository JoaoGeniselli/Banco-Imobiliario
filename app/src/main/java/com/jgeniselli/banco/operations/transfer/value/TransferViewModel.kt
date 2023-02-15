package com.jgeniselli.banco.operations.transfer.value

import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.compose.Player

class TransferViewModel : ViewModel() {

    fun onInputValue(double: Double) {

    }

    fun onSelectRecipient() {

    }
}

data class TransferState(
    val availableRecipients: List<Player>,
    val value: Double = 0.0,
    val selectedRecipientIndex: Int? = null
)