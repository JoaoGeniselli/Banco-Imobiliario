package com.jgeniselli.banco.domain

class CreditUseCase : UseCase<CreditUseCase.Request, Unit>() {

    override fun execute(input: Request, onComplete: (Unit, Throwable?) -> Unit) {
        with(input) {
            player.currentValue += value
        }
        onComplete(Unit, null)
    }

    data class Request(val player: Player, val value: Double)
}