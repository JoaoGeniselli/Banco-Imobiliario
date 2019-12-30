package com.jgeniselli.banco.domain

import java.lang.IllegalStateException

internal class CreateGameUseCase(
    private val playerRepository: PlayerRepository,
    private val gameRepository: GameRepository
) : UseCase<List<CreditCard>, Game?>() {

    companion object {
        const val MINIMUM_REQUIRED_PLAYERS = 2
        const val DEFAULT_INITIAL_CASH = 25000.0
    }

    override fun execute(input: List<CreditCard>, onComplete: (Game?, Throwable?) -> Unit) {
        if (input.size < MINIMUM_REQUIRED_PLAYERS) {
            return onComplete(
                null,
                IllegalStateException("Must have at least $MINIMUM_REQUIRED_PLAYERS players to create a game")
            )
        }

        try {
            val game = createGame(input)
            onComplete(game, null)
        } catch (e: Throwable) {
            onComplete(null, e)
        }
    }

    private fun createGame(input: List<CreditCard>): Game {
        val players = input.map {
            playerRepository.createPlayerFor(it, DEFAULT_INITIAL_CASH)
        }
        return gameRepository.createGameWithPlayers(players)
    }
}