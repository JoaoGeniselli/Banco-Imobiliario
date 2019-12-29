package com.jgeniselli.banco.game.common.domain

class Player(
    val id: Long,
    val creditCard: CreditCard,
    var currentValue: Double = 25000.0
)