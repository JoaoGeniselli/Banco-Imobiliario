package com.jgeniselli.banco.core

class GameSetup(val initialCash: Double, private val availablePlayerColors: List<ColorHex>) {

    val availableColorsInHex: List<String>
        get() = availablePlayerColors.map { it.hex }
}