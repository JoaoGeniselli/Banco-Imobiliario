package com.jgeniselli.banco.game.common.domain

interface TransactionRepository {

    fun saveTransaction(player: Player, value: Double)
}