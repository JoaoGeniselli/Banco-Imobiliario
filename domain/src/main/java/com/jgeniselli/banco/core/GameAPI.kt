package com.jgeniselli.banco.core

class GameAPI(
    private val storage: PlayerStorage
) {
    companion object {
        private const val INITIAL_CASH = 25000.0
    }

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
        val colors = PlayerColor.allAvailable()
        storage.clearPlayersAndTransactions {
            storage.createPlayersForColors(colors, INITIAL_CASH, callback)
        }
    }

    fun getTransactionHistory(callback: Callback<List<StoredTransactionDto>>) {
        return storage.findTransactionHistory(callback)
    }
}