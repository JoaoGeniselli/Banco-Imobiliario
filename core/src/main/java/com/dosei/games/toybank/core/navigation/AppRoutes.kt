package com.dosei.games.toybank.core.navigation

import kotlinx.serialization.Serializable

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
    data object Operations {

        @Serializable
        data object Withdraw

        @Serializable
        data object Deposit

        @Serializable
        data object Transfer
    }
}