package com.jgeniselli.banco.game.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgeniselli.banco.game.create.CreateGameLoader
import com.jgeniselli.banco.game.play.StartUpLoader

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.StartUp) {
        composable(Destinations.StartUp) {
            StartUpLoader(
                onGameUnavailable = {
                    navController.navigate(Destinations.GameCreation)
                },
                onContinueGame = {
                    navController.navigate(Destinations.Gameplay)
                }
            )
        }

        composable(Destinations.GameCreation) {
            CreateGameLoader(
                onStartGame = { navController.navigate(Destinations.Gameplay) }
            )
        }
    }


}