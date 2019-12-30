package com.jgeniselli.banco.domain

class Player(
    val id: Long,
    val creditCard: CreditCard,
    var currentValue: Double = 25000.0
)