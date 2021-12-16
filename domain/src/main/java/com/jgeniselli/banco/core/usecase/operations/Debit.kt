package com.jgeniselli.banco.core.usecase.operations

import com.jgeniselli.banco.core.Operation
import com.jgeniselli.banco.core.boundary.NewPlayerStorage
import com.jgeniselli.banco.core.usecase.players.FetchPlayerById

class Debit(
    private val fetchPlayerById: FetchPlayerById,
    private val playerStorage: NewPlayerStorage,
    private val logOperation: LogOperation
) {
    suspend operator fun invoke(playerId: Int, value: Long) {
        val player = fetchPlayerById(playerId)
        playerStorage.updateBalance(playerId, player.balance - value)
        logOperation(Operation.Debit(playerId, value))
    }
}