package com.jgeniselli.banco.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            HomeLoader(
                onContinueGame = { navController.navigate("ongoing_game") },
                onNewGame = { navController.navigate("new_game") }
            )
        }

        composable("new_game") {
            NewGameLoader(onStart = {})
        }
    }
}