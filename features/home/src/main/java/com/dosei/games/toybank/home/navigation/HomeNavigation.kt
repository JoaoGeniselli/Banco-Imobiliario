package com.dosei.games.toybank.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.home.presentation.HomeScreen

fun NavGraphBuilder.homeGraph(controller: NavHostController) {
    composable<AppRoutes.Home> {
        HomeScreen(controller, hiltViewModel())
    }
}