package com.jgeniselli.banco.game.play

import androidx.annotation.DrawableRes

data class GameplayPlayer(
    val id: Int,
    @DrawableRes val icon: Int,
    val name: String,
    val formattedBalance: String
)
