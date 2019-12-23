package com.jgeniselli.banco.game.common.domain

interface PlayerRepository {
    fun findAll(onSuccess: (List<Player>) -> Unit, onError: () -> Unit)
    fun findById(playerId: Int, onSuccess: (Player?) -> Unit)
    fun createPlayer(color: Color): Player
}

