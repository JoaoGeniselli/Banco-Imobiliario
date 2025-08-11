package com.dosei.games.toybank.feature.home

import com.dosei.games.toybank.core.data.repository.PlayerRepository
import javax.inject.Inject

class HasOngoingGame @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke() = playerRepository.countPlayers() > 0
}