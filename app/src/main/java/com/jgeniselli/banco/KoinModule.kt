package com.jgeniselli.banco

import com.jgeniselli.banco.game.common.domain.*
import com.jgeniselli.banco.game.create.CreateGameViewModel
import com.jgeniselli.banco.game.play.GameViewModel
import com.jgeniselli.banco.game.transaction.TransactionViewModel
import com.jgeniselli.banco.landing.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {

    val mainModule by lazy {
        module {
            single<GameRepository> { MemoryGameRepository() }
            single<PlayerRepository> { MemoryPlayerRepository() }
            single<TransactionRepository> { MemoryTransactionRepository() }
            single<ColorRepository> { MemoryColorRepository() }

            viewModel { MainViewModel(get()) }
            viewModel { CreateGameViewModel(get(), get(), get()) }
            viewModel { GameViewModel(get()) }
            viewModel { params -> TransactionViewModel(params[0], get(), get(), get()) }
        }
    }
}