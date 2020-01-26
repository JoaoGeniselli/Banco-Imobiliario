package com.jgeniselli.banco.core

import com.jgeniselli.banco.core.boundary.PlayerStorage
import com.jgeniselli.banco.core.dto.StoredPlayerDto
import com.jgeniselli.banco.core.dto.StoredTransactionDto

class GameAPI(
    private val gameSetup: GameSetup,
    private val storage: PlayerStorage
) {

    fun getPlayers(callback: Callback<List<StoredPlayerDto>>) {
        storage.findAllPlayers(callback)
    }

    fun debit(playerId: Long, value: Double, callback: ResultlessCallback) {
        ensurePositiveValue(value)
        addCashToPlayer(playerId, value * -1, callback)
    }

    private fun ensurePositiveValue(value: Double) {
        if (value < 0.0) throw IllegalTransactionValueException.mustBePositive()
    }

    private fun addCashToPlayer(
        playerId: Long,
        value: Double,
        callback: ResultlessCallback
    ) {
        storage.addTransaction(playerId, value, callback)
    }

    fun credit(playerId: Long, value: Double, callback: ResultlessCallback) {
        ensurePositiveValue(value)
        addCashToPlayer(playerId, value, callback)
    }

    fun transfer(
        sourcePlayerId: Long,
        destinationPlayerId: Long,
        value: Double,
        callback: ResultlessCallback
    ) {
        debit(sourcePlayerId, value) {
            credit(destinationPlayerId, value, callback)
        }
    }

    fun startGameIfNeeded(callback: ResultlessCallback = {}) {
        storage.isGameGoingOn { hasGame ->
            val thereIsNoGameToPlay = !hasGame
            if (thereIsNoGameToPlay) {
                resetGame(callback)
            } else {
                callback()
            }
        }
    }

    fun resetGame(callback: ResultlessCallback) {
        val colors = gameSetup.availableColorsInHex
        storage.clearPlayersAndTransactions {
            storage.createPlayersForColors(colors, gameSetup.initialCash, callback)
        }
    }

    fun getTransactionHistory(callback: Callback<List<StoredTransactionDto>>) {
        return storage.findTransactionHistory(callback)
    }
}