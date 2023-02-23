package com.jgeniselli.banco

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.jgeniselli.banco.game.history.HistoryScreen
import com.jgeniselli.banco.game.play.GamePlayScreen
import com.jgeniselli.banco.core.entity.OperationType
import com.jgeniselli.banco.home.HomeScreen
import com.jgeniselli.banco.newgame.NewGameScreen
import com.jgeniselli.banco.operations.credit.CreditScreen
import com.jgeniselli.banco.operations.debit.DebitScreen
import com.jgeniselli.banco.operations.transfer.TransferScreen
import com.jgeniselli.banco.topbar.component.TopBarAction
import com.jgeniselli.banco.topbar.component.createHistoryTopBarAction

private const val ARG_PLAYER_ID = "ARG_PLAYER_ID"

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME,
    onChangeActions: (List<TopBarAction>) -> Unit = {}
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(HOME) {
            HomeScreen(
                onContinueGame = { navController.navigate(GAMEPLAY) { popUpTo(0) } },
                onNewGame = { navController.navigate(NEW_GAME) }
            )
        }

        composable(NEW_GAME) {
            NewGameScreen(
                onStart = { navController.navigate(GAMEPLAY) { popUpTo(0) } }
            )
        }

        composable(GAMEPLAY) {
            SideEffect {
                onChangeActions(
                    listOf(createHistoryTopBarAction(navController))
                )
            }
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
            SideEffect { onChangeActions(emptyList()) }
            CreditScreen(
                playerId = entry.arguments?.getInt(ARG_PLAYER_ID) ?: 0,
                onOperationDone = { navController.popBackStack() }
            )
        }

        composable(
            route = "$DEBIT/{$ARG_PLAYER_ID}",
            arguments = listOf(navArgument(ARG_PLAYER_ID) { type = NavType.IntType })
        ) { entry ->
            SideEffect { onChangeActions(emptyList()) }
            DebitScreen(
                playerId = entry.arguments?.getInt(ARG_PLAYER_ID) ?: 0,
                onOperationDone = { navController.popBackStack() }
            )
        }

        composable(HISTORY) {
            SideEffect { onChangeActions(emptyList()) }
            HistoryScreen()
        }

        composable(
            route = "$TRANSFER/{$ARG_PLAYER_ID}",
            arguments = listOf(navArgument(ARG_PLAYER_ID) { type = NavType.IntType })
        ) { entry ->
            SideEffect { onChangeActions(emptyList()) }
            TransferScreen(
                playerId = entry.arguments?.getInt(ARG_PLAYER_ID) ?: 0,
                onOperationDone = { navController.popBackStack() }
            )
        }
    }
}
