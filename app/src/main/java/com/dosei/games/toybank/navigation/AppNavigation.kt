package com.dosei.games.toybank.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.gameplay.navigation.gameplayGraph
import com.dosei.games.toybank.history.navigation.historyGraph
import com.dosei.games.toybank.home.navigation.homeGraph
import com.dosei.games.toybank.newgame.navigation.newGameGraph
import com.dosei.games.toybank.transaction.navigation.transactionGraph

@Composable
fun AppNavigation(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = AppRoutes.Home,
    ) {
        homeGraph(controller)
        gameplayGraph(controller)
        transactionGraph(controller)
        newGameGraph(controller)
        historyGraph(controller)
    }
}