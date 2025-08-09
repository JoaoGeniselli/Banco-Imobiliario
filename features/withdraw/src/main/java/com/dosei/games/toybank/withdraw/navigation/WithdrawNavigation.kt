package com.dosei.games.toybank.withdraw.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dosei.games.toybank.core.navigation.AppRoutes

fun NavGraphBuilder.withdrawGraph(
    onWithdrawSuccess: () -> Unit,
) {
    navigation<AppRoutes.Operations.Withdraw>(
        startDestination = WithdrawRoutes.AmountInput
    ) {
        composable<WithdrawRoutes.AmountInput> {

        }

        composable<WithdrawRoutes.Receipt> {

        }
    }
}
