package com.jgeniselli.banco.newgame

import com.jgeniselli.banco.ui.component.PlayerSummary

data class NewGameUiState(
    val players: List<PlayerSummary> = listOf(),
    val isGameStartAllowed: Boolean = false,
    val canAddNewPlayer: Boolean = true,
    val isGameStarted: Boolean = false
)
