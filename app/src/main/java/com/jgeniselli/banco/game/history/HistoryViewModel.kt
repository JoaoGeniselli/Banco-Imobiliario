package com.jgeniselli.banco.game.history

import androidx.lifecycle.ViewModel
import com.jgeniselli.banco.core.repository.GameRepository
import kotlinx.coroutines.flow.map

class HistoryViewModel(
    gameRepository: GameRepository,
    formatter: OperationFormatter
) : ViewModel() {

    val history = gameRepository.history.map { operations ->
        operations.map { operation -> formatter.format(operation) }.reversed()
    }
}