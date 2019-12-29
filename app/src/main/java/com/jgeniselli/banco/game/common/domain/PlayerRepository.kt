package com.jgeniselli.banco.game.common.domain

interface PlayerRepository {
    fun findAll(onSuccess: (List<Player>) -> Unit, onError: () -> Unit)
    fun findById(playerId: Long, onSuccess: (Player?) -> Unit)
    fun createPlayer(creditCard: CreditCard): Player
}

