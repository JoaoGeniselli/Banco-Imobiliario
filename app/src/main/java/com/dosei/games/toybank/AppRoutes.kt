package com.dosei.games.toybank

import kotlinx.serialization.Serializable

object AppRoutes {

    @Serializable
    data object Home

    @Serializable
    data object GameSetup

    @Serializable
    data object Gameplay
}