package com.jgeniselli.banco.game.play

import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.R
import com.jgeniselli.banco.compose.GameRepository
import com.jgeniselli.banco.ui.component.toCurrency
import kotlinx.coroutines.flow.map

class GamePlayViewModel(
    gameRepository: GameRepository,
) : ViewModel() {
    val players = gameRepository.players.map { result ->
        result.map { player ->
            GameplayPlayer(
                player.id,
                R.drawable.ic_baseline_pets_24,
                player.name,
                player.balance.toCurrency()
            )
        }
    }
}