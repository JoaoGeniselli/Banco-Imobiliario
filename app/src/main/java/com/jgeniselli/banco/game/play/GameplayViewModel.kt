package com.jgeniselli.banco.game.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.core.Player
import com.jgeniselli.banco.core.usecase.operations.Credit
import com.jgeniselli.banco.core.usecase.operations.Debit
import com.jgeniselli.banco.core.usecase.operations.Transfer
import com.jgeniselli.banco.core.usecase.players.FetchPlayers
import com.jgeniselli.banco.game.common.Currency
import kotlinx.coroutines.launch

class GameplayViewModel(
    private val fetchPlayers: FetchPlayers,
    private val credit: Credit,
    private val debit: Debit,
    private val transfer: Transfer,
    private val currency: Currency
) : ViewModel() {

    private lateinit var cachedPlayers: List<Player>

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> get() = _players

    fun start() {
        viewModelScope.launch {
            updatePlayers()
        }
    }

    private suspend fun updatePlayers() {
        cachedPlayers = fetchPlayers()
        _players.value = cachedPlayers.map { it.toPlayerModel() }
    }

    private fun Player.toPlayerModel() = PlayerModel(
        id = id,
        name = name,
        balance = currency.format(balance)
    )

    fun onCreditRequested(playerId: Int, value: String) {
        val valueInDouble = currency.parse(value) ?: return
        viewModelScope.launch {
            credit(playerId, valueInDouble)
            updatePlayers()
        }
    }

    fun onDebitRequested(playerId: Int, value: String) {
        val valueInDouble = currency.parse(value) ?: return
        viewModelScope.launch {
            debit(playerId, valueInDouble)
            updatePlayers()
        }
    }

    fun onTransferRequested(senderId: Int, receiverId: Int, value: String) {
        val valueInDouble = currency.parse(value) ?: return
        viewModelScope.launch {
            transfer(senderId, receiverId, valueInDouble)
            updatePlayers()
        }
    }
}