package com.jgeniselli.banco.domain

internal class DebitUseCase(
    private val playerRepository: PlayerRepository
) : UseCase<BalanceUpdateRequest, Unit>() {

    override fun execute(input: BalanceUpdateRequest, onComplete: (Unit, Throwable?) -> Unit) {
        with(input) {
            player.currentValue -= value
            playerRepository.save(player)
        }
        onComplete(Unit, null)
    }

}