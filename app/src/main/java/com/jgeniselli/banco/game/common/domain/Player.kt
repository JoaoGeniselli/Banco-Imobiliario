package com.jgeniselli.banco.game.common.domain

interface Player {
    val id: Int
    val colorHex: String
    val name: String
    var currentValue: Double
}