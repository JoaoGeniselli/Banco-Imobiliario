package com.jgeniselli.banco.core.usecase.game

import com.jgeniselli.banco.core.ErrorCode
import com.jgeniselli.banco.core.GameException
import com.jgeniselli.banco.core.GameRules
import com.jgeniselli.banco.core.boundary.GameStorage
import com.jgeniselli.banco.core.boundary.NewPlayerStorage

class StartGame(
    private val gameStorage: GameStorage,
    private val playerStorage: NewPlayerStorage
) {
    suspend operator fun invoke(
        players: List<String>,
        initialValue: Long = GameRules.DefaultInitialValue
    ) {
        if (players.distinct().size < players.size) {
            throw GameException(ErrorCode.DUPLICATED_PLAYERS)
        }
        if (initialValue < GameRules.MinInitialValue) {
            throw GameException(ErrorCode.INVALID_INITIAL_VALUE)
        }
        if (players.size < GameRules.MinPlayerAmount) {
            throw GameException(ErrorCode.INVALID_PLAYER_AMOUNT)
        }
        gameStorage.saveInitialValue(initialValue)
        playerStorage.createPlayers(initialValue, players)
    }
}