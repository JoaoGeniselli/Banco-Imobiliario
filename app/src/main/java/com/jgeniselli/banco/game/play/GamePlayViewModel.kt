package com.jgeniselli.banco.game.play

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.R
import com.jgeniselli.banco.compose.GameRepository
import com.jgeniselli.banco.compose.ui.theme.PlayerRed
import com.jgeniselli.banco.ui.component.toCurrency
import kotlinx.coroutines.flow.map

class GamePlayViewModel(
    gameRepository: GameRepository,
) : ViewModel() {
    val players = gameRepository.players.map { result ->
        result.map { player ->
            GameplayPlayer(
                player.id,
                Color(player.color),
                player.name,
                player.balance.toCurrency()
            )
        }
    }
}