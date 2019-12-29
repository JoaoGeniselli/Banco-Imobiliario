package com.jgeniselli.banco.game.common.domain

class Player(
    val id: Long,
    val color: Color,
    var currentValue: Double = 25000.0
)