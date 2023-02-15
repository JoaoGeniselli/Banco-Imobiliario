package com.jgeniselli.banco.game.play

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class GameplayPlayer(
    val id: Int,
    val color: Color,
    val name: String,
    val formattedBalance: String
)
