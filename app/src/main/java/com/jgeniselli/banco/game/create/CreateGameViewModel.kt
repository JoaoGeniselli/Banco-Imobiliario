package com.jgeniselli.banco.game.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeniselli.banco.R
import com.jgeniselli.banco.core.ErrorCode
import com.jgeniselli.banco.core.GameException
import com.jgeniselli.banco.core.GameRules
import com.jgeniselli.banco.core.usecase.game.StartGame
import com.jgeniselli.banco.game.common.SingleLiveEvent
import kotlinx.coroutines.launch

class CreateGameViewModel(
    private val startGame: StartGame
) : ViewModel() {

    private val _redirectToGame = SingleLiveEvent<Unit>()
    val redirectToGame: LiveData<Unit> get() = _redirectToGame

    private val _buttonIsEnabled = SingleLiveEvent<Boolean>()
    val buttonIsEnabled: LiveData<Boolean> get() = _buttonIsEnabled

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> get() = _error

    private val cachedPlayers = mutableListOf<String>()
    private val _players = MutableLiveData<List<String>>()
    val players: LiveData<List<String>> get() = _players

    fun onAddPlayer(player: String) {
        cachedPlayers.add(player)
        _players.value = cachedPlayers.toList()
        updateButtonStatus()
    }

    private fun updateButtonStatus() {
        _buttonIsEnabled.value = cachedPlayers.size >= GameRules.MinPlayerAmount
    }

    fun onClickStartGame() {
        viewModelScope.launch {
            try {
                startGame(cachedPlayers)
                _redirectToGame.value = Unit
            } catch (exception: GameException) {
                handleError(exception)
            }
        }
    }

    private fun handleError(exception: GameException) {
        _error.value = when (exception.code) {
            ErrorCode.DUPLICATED_PLAYERS -> R.string.error_duplicated_players
            ErrorCode.INVALID_PLAYER_AMOUNT -> R.string.error_unsufficient_players_amount
            else -> R.string.error_start_game_generic
        }
    }
}