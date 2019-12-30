package com.jgeniselli.banco.domain

interface GameRepository {

    fun getCurrentGame() : Game?
    fun createGameWithPlayers(players: List<Player>) : Game
}