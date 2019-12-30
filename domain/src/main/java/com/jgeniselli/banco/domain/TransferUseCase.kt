package com.jgeniselli.banco.domain

internal class TransferUseCase(
    private val playerRepository: PlayerRepository
) : UseCase<TransferRequest, Unit>() {

    override fun execute(input: TransferRequest, onComplete: (Unit, Throwable?) -> Unit) {
        with(input) {
            sourcePlayer.currentValue -= value
            destinationPlayer.currentValue += value

            playerRepository.save(sourcePlayer)
            playerRepository.save(destinationPlayer)
        }
        onComplete(Unit, null)
    }
}