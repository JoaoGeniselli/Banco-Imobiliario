package com.jgeniselli.banco.game.common.domain

interface Game {
    val id: Int
    val players: List<Player>
    val transactions: List<Transaction>
}

