package com.jgeniselli.banco.game.common.domain

interface Player {
    val id: Int
//    val color: Color
    val colorHex: String
    val name: String
    var currentValue: Double
}