package com.dosei.games.toybank.transaction.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.dosei.games.toybank.commons.navigation.sharedViewModel
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.transaction.TransactionViewModel
import com.dosei.games.toybank.transaction.amount.TransactionAmountScreen
import com.dosei.games.toybank.transaction.type.TransactionTypeScreen

fun NavGraphBuilder.transactionGraph(
    controller: NavHostController,
    modifier: Modifier,
) {
    navigation<AppRoutes.Transaction>(
        startDestination = TransactionRoutes.TypeSelection(playerId = 0) // playerId will be overridden
    ) {

        composable<TransactionRoutes.TypeSelection> { entry ->
            val route = entry.toRoute<TransactionRoutes.TypeSelection>()
            val viewModel = entry.sharedViewModel<TransactionViewModel>(controller)

            LaunchedEffect(Unit) {
                viewModel.load(route.playerId)
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

        composable<TransactionRoutes.Receipt> {

        }
    }
}
