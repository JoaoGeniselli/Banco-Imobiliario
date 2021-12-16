package com.jgeniselli.banco.core.usecase.operations

import com.jgeniselli.banco.core.Operation
import com.jgeniselli.banco.core.boundary.NewPlayerStorage
import com.jgeniselli.banco.core.usecase.players.FetchPlayerById

class Transfer(
    private val fetchPlayerById: FetchPlayerById,
    private val playerStorage: NewPlayerStorage,
    private val logOperation: LogOperation
) {
    suspend operator fun invoke(senderId: Int, receiverId: Int, value: Long) {
        val sender = fetchPlayerById(senderId)
        val receiver = fetchPlayerById(receiverId)
        playerStorage.run {
            updateBalance(senderId, sender.balance - value)
            updateBalance(receiverId, receiver.balance + value)
        }
        logOperation(Operation.Transfer(senderId, receiverId, value))
    }
}