package com.jgeniselli.banco.domain

interface PlayerRepository {

    fun createPlayerFor(creditCard: CreditCard, initialCash: Double) : Player
    fun save(player: Player)
}