package com.jgeniselli.banco.game.common.domain

interface GameAPI {

    fun getAvailableColors(onSuccess: (List<Color>) -> Unit)

    fun getCurrentPlayers(onSuccess: (List<Player>) -> Unit)

    fun updateCreditCards(player: Player, cash: Double)
}