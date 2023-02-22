package com.jgeniselli.banco.game.play

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.ui.toolbox.toCurrency
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