package com.jgeniselli.banco.game.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.getViewModel

@Composable
fun StartUpLoader(
    viewModel: StartUpViewModel = getViewModel(),
    onGameUnavailable: () -> Unit,
    onContinueGame: () -> Unit
) {
    val redirectToGameCreation = viewModel.initGameCreation.observeAsState()
    val redirectToGameplay = viewModel.redirectToOngoingGame.observeAsState()
    when {
        redirectToGameCreation.value != null -> onGameUnavailable()
        redirectToGameplay.value != null -> onContinueGame()
        else -> {
            StartUp()
            viewModel.start()
        }
    }
}

@Composable
fun StartUp() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}