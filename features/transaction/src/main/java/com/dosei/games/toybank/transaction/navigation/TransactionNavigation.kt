package com.dosei.games.toybank.transaction.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.dosei.games.toybank.commons.navigation.sharedViewModel
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.transaction.TransactionViewModel
import com.dosei.games.toybank.transaction.screen.TransactionAmountScreen
import com.dosei.games.toybank.transaction.screen.TransactionTypeScreen
import com.dosei.games.toybank.transaction.screen.beneficiary.TransactionBeneficiaryScreen

fun NavGraphBuilder.transactionGraph(
    controller: NavHostController,
) {
    navigation<AppRoutes.Transaction>(
        startDestination = TransactionRoutes.TypeSelection(playerId = 0) // playerId will be overridden
    ) {

        composable<TransactionRoutes.TypeSelection> { entry ->
            val route = entry.toRoute<TransactionRoutes.TypeSelection>()
            val viewModel = entry.sharedViewModel<TransactionViewModel>(controller)

            LaunchedEffect(Unit) {
                viewModel.start(route.playerId)
            }

            TransactionTypeScreen(
                controller = controller,
                viewModel = viewModel
            )
        }

        composable<TransactionRoutes.AmountInput> { entry ->
            TransactionAmountScreen(
                controller = controller,
                viewModel = entry.sharedViewModel(controller),
            )
        }

        composable<TransactionRoutes.BeneficiarySelection> { entry ->
            TransactionBeneficiaryScreen(
                controller = controller,
                parentViewModel = entry.sharedViewModel(controller),
                viewModel = hiltViewModel()
            )
        }

    }
}
