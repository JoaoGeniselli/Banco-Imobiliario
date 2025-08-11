package com.dosei.games.toybank.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object AppRoutes {

    @Serializable
    data object Home

    @Serializable
    data object Game {
        @Serializable
        data object Setup

        @Serializable
        data object Play
    }

    @Serializable
    data class Transaction(val playerId: Int)
}