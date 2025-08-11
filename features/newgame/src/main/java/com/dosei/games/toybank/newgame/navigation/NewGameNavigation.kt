package com.dosei.games.toybank.newgame.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.newgame.presentation.NewGameScreen

fun NavGraphBuilder.newGameGraph(controller: NavHostController) {
    composable<AppRoutes.Game.New> {
        NewGameScreen(controller, hiltViewModel())
    }
}