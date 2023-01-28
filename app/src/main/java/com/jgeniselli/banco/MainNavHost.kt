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
import com.jgeniselli.banco.game.play.PlayerOptionsLoader
import com.jgeniselli.banco.home.HomeLoader

// Routes
private const val HOME = "home"
private const val NEW_GAME = "new_game"
private const val GAMEPLAY = "gameplay"
private const val PLAYER_OPTIONS = "player_options"
private const val CREDIT = "credit"
private const val DEBIT = "debit"
private const val TRANSFER = "transfer"
private const val TRANSFER_INPUT_VALUE = "transfer_input_value"
private const val TRANSFER_INPUT_RECIPIENT = "transfer_input_recipient"
private const val TRANSFER_CONCLUSION = "transfer_conclusion"

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
                onTransfer = { selected -> /* Send do Transfer */ }
            )
        }

        composable(PLAYER_OPTIONS) {
            PlayerOptionsLoader(
                onSelectOption = { option ->
                    val route = when (option) {
                        PlayerOptionType.TRANSFER -> TRANSFER
                        PlayerOptionType.DEBIT -> DEBIT
                        PlayerOptionType.CREDIT -> CREDIT
                    }
                    navController.navigate(route)
                }
            )
        }

        navigation(startDestination = TRANSFER_INPUT_VALUE, route = TRANSFER) {
            composable(TRANSFER_INPUT_VALUE) {}

            composable(TRANSFER_INPUT_RECIPIENT) {}

            composable(TRANSFER_CONCLUSION) {}
        }


    }
}
