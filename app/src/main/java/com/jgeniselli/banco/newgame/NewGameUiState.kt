package com.jgeniselli.banco.newgame

import com.jgeniselli.banco.compose.PlayerSummary

data class NewGameUiState(
    val players: List<PlayerSummary> = listOf(),
    val isGameStartAllowed: Boolean = false,
    val canAddNewPlayer: Boolean = true
)
