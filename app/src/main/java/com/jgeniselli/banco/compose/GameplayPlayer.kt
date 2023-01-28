package com.jgeniselli.banco.compose

import androidx.annotation.DrawableRes

data class GameplayPlayer(
    @DrawableRes val icon: Int,
    val name: String,
    val formattedBalance: String
)
