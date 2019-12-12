package com.jgeniselli.banco.game.common.domain

interface Transaction {
    val player: Player
    val value: Double
}