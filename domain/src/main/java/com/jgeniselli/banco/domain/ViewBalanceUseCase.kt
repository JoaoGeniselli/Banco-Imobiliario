package com.jgeniselli.banco.domain

internal class ViewBalanceUseCase(
    private val gameRepository: GameRepository
) : UseCase<Unit, List<Player>?>() {

    override fun execute(input: Unit, onComplete: (List<Player>?, Throwable?) -> Unit) {
        gameRepository.getCurrentGame()?.let {
            onComplete(it.players, null)
        } ?: onComplete(null, IllegalStateException("There is no game to view the players balance"))
    }
}