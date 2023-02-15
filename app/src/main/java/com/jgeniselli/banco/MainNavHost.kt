package com.jgeniselli.banco

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jgeniselli.banco.game.creation.NewGameLoader
import com.jgeniselli.banco.game.play.GamePlayScreen
import com.jgeniselli.banco.game.play.OperationType
import com.jgeniselli.banco.home.HomeLoader
import com.jgeniselli.banco.operations.credit.CreditScreen
import com.jgeniselli.banco.operations.debit.DebitScreen

// Routes
private const val HOME = "HOME"
private const val NEW_GAME = "NEW_GAME"
private const val GAMEPLAY = "GAMEPLAY"
private const val CREDIT = "CREDIT"
private const val CREDIT_INPUT_VALUE = "CREDIT_INPUT_VALUE"
private const val CREDIT_CONCLUSION = "CREDIT_CONCLUSION"
private const val DEBIT = "DEBIT"
private const val DEBIT_INPUT_VALUE = "DEBIT_INPUT_VALUE"
private const val DEBIT_CONCLUSION = "DEBIT_CONCLUSION"
private const val TRANSFER = "TRANSFER"
private const val TRANSFER_INPUT_VALUE = "TRANSFER_INPUT_VALUE"
private const val TRANSFER_INPUT_RECIPIENT = "TRANSFER_INPUT_RECIPIENT"
private const val TRANSFER_CONCLUSION = "TRANSFER_CONCLUSION"

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

        navigation(startDestination = TRANSFER_INPUT_VALUE, route = TRANSFER) {
            composable(TRANSFER_INPUT_VALUE) {}

            composable(TRANSFER_INPUT_RECIPIENT) {}

            composable(TRANSFER_CONCLUSION) {}
        }


    }
}
