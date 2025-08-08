package com.dosei.games.toybank.feature.game.setup

import androidx.compose.ui.graphics.Color
import com.dosei.games.toybank.data.model.LeadPlayer
import com.dosei.games.toybank.ui.theme.Amber
import com.dosei.games.toybank.ui.theme.Blue500
import com.dosei.games.toybank.ui.theme.DeepOrange
import com.dosei.games.toybank.ui.theme.Green
import com.dosei.games.toybank.ui.theme.Grey
import com.dosei.games.toybank.ui.theme.Purple

data class GameSetupState(
    val players: List<LeadPlayer> = emptyList(),
    val initialBalanceInCents: Int = 5000_00,
    val availableColors: List<Color> = listOf(
        Purple,
        DeepOrange,
        Green,
        Amber,
        Grey,
        Blue500,
    ),
    val isLoading: Boolean = false,
)