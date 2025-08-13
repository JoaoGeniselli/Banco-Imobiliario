package com.dosei.games.toybank.home.data.usecase

import com.dosei.games.toybank.core.data.repository.PlayerRepository
import javax.inject.Inject

internal class HasOngoingGame @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke() = playerRepository.countPlayers() > 0
}