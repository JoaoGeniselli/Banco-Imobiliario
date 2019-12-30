package com.jgeniselli.banco.domain

object UseCaseAPI {

    private lateinit var playerRepository: PlayerRepository
    private lateinit var gameRepository: GameRepository

    fun debit() : UseCase<BalanceUpdateRequest, Unit> = DebitUseCase(playerRepository)

    fun credit() : UseCase<BalanceUpdateRequest, Unit> = CreditUseCase(playerRepository)

    fun transfer() : UseCase<TransferRequest, Unit> = TransferUseCase(playerRepository)

    fun viewBalance() : UseCase<Unit, List<Player>?> = ViewBalanceUseCase(gameRepository)

    fun inject(playerRepository: PlayerRepository, gameRepository: GameRepository) {
        this.playerRepository = playerRepository
        this.gameRepository = gameRepository
    }
}