package com.dosei.games.toybank.gameplay.navigation

import kotlinx.serialization.Serializable

internal object GameplayRoutes {

    @Serializable
    data object Gameplay

    @Serializable
    data class Winner(val name: String, val colorARGB: Int)
}