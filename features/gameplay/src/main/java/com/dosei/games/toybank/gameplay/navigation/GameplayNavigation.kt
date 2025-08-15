package com.dosei.games.toybank.gameplay.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.gameplay.presentation.play.GameplayScreen
import com.dosei.games.toybank.gameplay.presentation.winner.WinnerScreen

fun NavGraphBuilder.gameplayGraph(controller: NavHostController) {
    navigation<AppRoutes.Game.Play>(
        startDestination = GameplayRoutes.Gameplay
    ) {
        composable<GameplayRoutes.Gameplay> {
            GameplayScreen(controller, hiltViewModel())
        }

        composable<GameplayRoutes.Winner> { entry ->
            val route = entry.toRoute<GameplayRoutes.Winner>()
            WinnerScreen(
                controller = controller,
                winnerName = route.name,
                winnerColor = route.colorARGB
            )
        }
    }
}