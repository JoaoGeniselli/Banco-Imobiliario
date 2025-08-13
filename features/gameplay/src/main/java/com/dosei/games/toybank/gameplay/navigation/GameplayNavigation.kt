package com.dosei.games.toybank.gameplay.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.gameplay.presentation.GameplayScreen

fun NavGraphBuilder.gameplayGraph(controller: NavHostController) {
    composable<AppRoutes.Game.Play> {
        GameplayScreen(controller, hiltViewModel())
    }
}