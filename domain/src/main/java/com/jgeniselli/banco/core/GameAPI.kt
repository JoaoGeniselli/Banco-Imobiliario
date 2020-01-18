package com.jgeniselli.banco.core

class GameAPI(
    private val storage: PlayerStorage
) {
    companion object {
        private const val INITIAL_CASH = 25000.0
    }

    suspend fun getPlayers(): List<StoredPlayerDto> {
        return storage.findAllPlayers()
    }

    suspend fun debit(playerId: Long, value: Double) {
        ensurePositiveValue(value)
        addCashToPlayer(playerId, value * -1)
    }

    private fun ensurePositiveValue(value: Double) {
        if (value < 0.0) throw IllegalTransactionValueException.mustBePositive()
    }

    private suspend fun addCashToPlayer(playerId: Long, value: Double) {
        ensurePlayerExists(playerId)
        storage.addTransaction(playerId, value)
    }

    private suspend fun ensurePlayerExists(playerId: Long) {
        storage.findById(playerId) ?: throw UnknownPlayerException(playerId)
    }

    suspend fun credit(playerId: Long, value: Double) {
        ensurePositiveValue(value)
        addCashToPlayer(playerId, value)
    }

    suspend fun transfer(sourcePlayerId: Long, destinationPlayerId: Long, value: Double) {
        debit(sourcePlayerId, value)
        credit(destinationPlayerId, value)
    }

    suspend fun startNewGame() {
        val colors = PlayerColor.allAvailable()
        storage.clearPlayers()
        storage.createPlayersForColors(colors, INITIAL_CASH)
    }
}