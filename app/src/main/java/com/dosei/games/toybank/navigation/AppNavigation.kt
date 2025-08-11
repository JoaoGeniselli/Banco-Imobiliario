package com.dosei.games.toybank.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.feature.game.play.GameplayScreen
import com.dosei.games.toybank.feature.home.HomeScreen
import com.dosei.games.toybank.newgame.navigation.newGameGraph
import com.dosei.games.toybank.transaction.navigation.transactionGraph

@Composable
fun AppNavigation(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = AppRoutes.Home,
    ) {
        composable<AppRoutes.Home> {
            HomeScreen(controller, hiltViewModel())
        }

        composable<AppRoutes.Game.Play> {
            GameplayScreen(controller, hiltViewModel())
        }

        transactionGraph(controller)
        newGameGraph(controller)

    }
}