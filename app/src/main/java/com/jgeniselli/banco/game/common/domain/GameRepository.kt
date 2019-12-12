package com.jgeniselli.banco.game.common.domain

interface GameRepository {
    fun getActiveGame() : Game?
    fun createAndActivateGame(selectedPlayers: List<Player>)
}