package com.dosei.games.toybank.transaction.navigation

import kotlinx.serialization.Serializable

@Serializable
data object TransactionRoutes {

    @Serializable
    data class TypeSelection(val playerId: Int)

    @Serializable
    data object BeneficiarySelection

    @Serializable
    data object AmountInput

    @Serializable
    data object Receipt
}