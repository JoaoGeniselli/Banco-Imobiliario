package com.jgeniselli.banco.game.transaction

interface TransactionService {
    fun debit(playerId: Long, value: Double)
    fun credit(playerId: Long, value: Double)
}