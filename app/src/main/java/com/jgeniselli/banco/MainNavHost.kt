package com.jgeniselli.banco

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jgeniselli.banco.game.creation.NewGameLoader
import com.jgeniselli.banco.game.play.GamePlayLoader
import com.jgeniselli.banco.game.play.PlayerOptionType
import com.jgeniselli.banco.game.play.PlayerOptionsBottomSheet
import com.jgeniselli.banco.home.HomeLoader

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
            GamePlayLoader(
                onSelectOperation = { player, operation ->
                    val route = when (operation) {
                        PlayerOptionType.TRANSFER -> TRANSFER
                        PlayerOptionType.DEBIT -> DEBIT
                        PlayerOptionType.CREDIT -> CREDIT
                    }
//                    navController.navigate(route)
                },
            )
        }

        navigation(startDestination = CREDIT_INPUT_VALUE, route = CREDIT) {
            composable(CREDIT_INPUT_VALUE) {
                // INPUT (SELECTED PLAYER)
            }

            composable(CREDIT_CONCLUSION) {}
        }

        navigation(startDestination = TRANSFER_INPUT_VALUE, route = TRANSFER) {
            composable(TRANSFER_INPUT_VALUE) {}

            composable(TRANSFER_INPUT_RECIPIENT) {}

            composable(TRANSFER_CONCLUSION) {}
        }


    }
}
