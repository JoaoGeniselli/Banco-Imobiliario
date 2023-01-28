package com.jgeniselli.banco.game.play

import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.R
import com.jgeniselli.banco.compose.GameRepository
import kotlinx.coroutines.flow.map
import java.text.NumberFormat

class GamePlayViewModel(
    val gameRepository: GameRepository,
    val balanceFormatter: NumberFormat
) : ViewModel() {

    val players = gameRepository.players.map { result ->
        result.map { player ->
            GameplayPlayer(
                R.drawable.ic_baseline_pets_24,
                player.name,
                balanceFormatter.format(player.balance)
            )
        }
    }
}