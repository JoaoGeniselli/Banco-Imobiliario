package com.dosei.games.toybank.withdraw.navigation

import kotlinx.serialization.Serializable

object WithdrawRoutes {

    @Serializable
    data object AmountInput

    @Serializable
    data object Receipt
}