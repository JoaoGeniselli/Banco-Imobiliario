package com.dosei.games.toybank.transaction

import androidx.lifecycle.viewModelScope
import com.dosei.games.toybank.core.base.StatefulViewModel
import com.dosei.games.toybank.core.data.model.Close
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.transaction.data.usecase.PerformTransaction
import com.dosei.games.toybank.transaction.navigation.TransactionRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TransactionViewModel @Inject constructor(
    private val performTransaction: PerformTransaction
) : StatefulViewModel<TransactionState>(
    initialState = TransactionState()
) {

    fun start(playerId: Int) {
        updateState { old -> old.copy(playerId = playerId) }
    }

    fun onSelectType(type: TransactionType) {
        viewModelScope.launch {
            updateState { old -> old.copy(type = type) }
            if (type == TransactionType.TRANSFER) {
                sendEvent(NavigateTo(TransactionRoutes.BeneficiarySelection))
            } else {
                sendEvent(NavigateTo(TransactionRoutes.AmountInput))
            }
        }
    }

    fun onBeneficiarySelected(beneficiaryId: Int) {
        viewModelScope.launch {
            updateState { old -> old.copy(destinationPlayerId = beneficiaryId) }
            sendEvent(NavigateTo(TransactionRoutes.AmountInput))
        }
    }

    fun onConfirmAmount(amount: Int) {
        request {
            updateState { old -> old.copy(amountInCents = amount) }
            performTransaction(state.value)
            sendEvent(Close)
        }
    }
}