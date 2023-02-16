package com.jgeniselli.banco

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jgeniselli.banco.Routes.CREDIT
import com.jgeniselli.banco.Routes.DEBIT
import com.jgeniselli.banco.Routes.GAMEPLAY
import com.jgeniselli.banco.Routes.HISTORY
import com.jgeniselli.banco.Routes.HOME
import com.jgeniselli.banco.Routes.NEW_GAME
import com.jgeniselli.banco.Routes.TRANSFER
import com.jgeniselli.banco.game.creation.NewGameLoader
import com.jgeniselli.banco.game.history.HistoryScreen
import com.jgeniselli.banco.game.play.GamePlayScreen
import com.jgeniselli.banco.game.play.OperationType
import com.jgeniselli.banco.home.HomeLoader
import com.jgeniselli.banco.operations.credit.CreditScreen
import com.jgeniselli.banco.operations.debit.DebitScreen

private const val ARG_PLAYER_ID = "ARG_PLAYER_ID"

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(HOME) {
            HomeLoader(
                onContinueGame = { navController.navigate(GAMEPLAY) },
                onNewGame = { navController.navigate(NEW_GAME) }
            )
        }

        composable(NEW_GAME) {
            NewGameLoader(
                onStart = { navController.navigate(GAMEPLAY) }
            )
        }

        composable(GAMEPLAY) {
            GamePlayScreen(
                onSelectOperation = { player, operation ->
                    val route = when (operation) {
                        OperationType.TRANSFER -> TRANSFER
                        OperationType.DEBIT -> DEBIT
                        OperationType.CREDIT -> CREDIT
                    }
                    navController.navigate("$route/${player.id}")
                },
            )
        }

        composable(
            route = "$CREDIT/{$ARG_PLAYER_ID}",
            arguments = listOf(navArgument(ARG_PLAYER_ID) { type = NavType.IntType })
        ) { entry ->
            CreditScreen(
                playerId = entry.arguments?.getInt(ARG_PLAYER_ID) ?: 0,
                onOperationDone = { navController.popBackStack() }
            )
        }

        composable(
            route = "$DEBIT/{$ARG_PLAYER_ID}",
            arguments = listOf(navArgument(ARG_PLAYER_ID) { type = NavType.IntType })
        ) { entry ->
            DebitScreen(
                playerId = entry.arguments?.getInt(ARG_PLAYER_ID) ?: 0,
                onOperationDone = { navController.popBackStack() }
            )
        }

        composable(HISTORY) {
            HistoryScreen()
        }
    }
}
