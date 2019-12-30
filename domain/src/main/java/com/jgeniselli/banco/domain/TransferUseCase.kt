package com.jgeniselli.banco.domain

class TransferUseCase : UseCase<TransferUseCase.Request, Unit>() {

    override fun execute(input: Request, onComplete: (Unit, Throwable?) -> Unit) {
        with(input) {
            sourcePlayer.currentValue -= value
            destinationPlayer.currentValue += value
        }
        onComplete(Unit, null)
    }

    data class Request(
        val sourcePlayer: Player,
        val destinationPlayer: Player,
        val value: Double
    )
}