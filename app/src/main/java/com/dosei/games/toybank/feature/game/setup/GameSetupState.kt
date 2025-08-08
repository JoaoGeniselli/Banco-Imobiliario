package com.dosei.games.toybank.feature.game.setup

import com.dosei.games.toybank.data.model.LeadPlayer

data class GameSetupState(
    val players: List<LeadPlayer> = emptyList(),
    val initialBalanceInCents: Int = 5000_00,
    val isLoading: Boolean = false,
)