package com.jgeniselli.banco.game.play

import androidx.annotation.DrawableRes

data class GameplayPlayer(
    @DrawableRes val icon: Int,
    val name: String,
    val formattedBalance: String
)
