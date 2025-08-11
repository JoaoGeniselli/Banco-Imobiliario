package com.dosei.games.toybank.transaction.screen.beneficiary

import androidx.lifecycle.ViewModel
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TransactionBeneficiaryViewModel @Inject constructor(
    private val repository: PlayerRepository
)  : ViewModel() {

    fun loadPlayers(playerId: Int) = repository.players
        .map { allPlayers -> allPlayers.filter { it.id != playerId } }
}