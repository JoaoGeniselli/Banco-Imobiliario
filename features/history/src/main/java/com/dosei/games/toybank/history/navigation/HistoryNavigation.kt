package com.dosei.games.toybank.history.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.history.presentation.HistoryScreen

fun NavGraphBuilder.historyGraph(controller: NavHostController) {
    composable<AppRoutes.Game.History> {
        HistoryScreen(controller, hiltViewModel())
    }
}