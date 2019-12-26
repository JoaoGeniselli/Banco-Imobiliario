package com.jgeniselli.banco.game.common.domain

import java.util.*

class Game(
    val id: Int,
    val createTime: Date,
    val players: List<Player>,
    val transactions: List<Transaction> = listOf()
)

