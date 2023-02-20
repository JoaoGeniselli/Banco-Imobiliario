package com.jgeniselli.banco

import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.core.repository.GameRepositoryImpl
import com.jgeniselli.banco.core.repository.GameStorage
import com.jgeniselli.banco.core.usecase.HasOngoingGame
import com.jgeniselli.banco.game.history.HistoryViewModel
import com.jgeniselli.banco.game.history.OperationFormatter
import com.jgeniselli.banco.game.play.GamePlayViewModel
import com.jgeniselli.banco.home.HomeViewModel
import com.jgeniselli.banco.infra.MemoryGameStorage
import com.jgeniselli.banco.newgame.NewGameViewModel
import com.jgeniselli.banco.operations.credit.CreditViewModel
import com.jgeniselli.banco.operations.debit.DebitViewModel
import com.jgeniselli.banco.operations.transfer.TransferViewModel
import com.jgeniselli.banco.ui.component.CurrencyValueResolver
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyInjection {

    val mainModule by lazy {
        module {
            factory { OperationFormatter(androidContext()) }
            single<GameStorage> { MemoryGameStorage() }
            single<GameRepository> { GameRepositoryImpl(get()) }

            // VIEW MODELS
            viewModel { HomeViewModel(HasOngoingGame()) }
            viewModel { NewGameViewModel(get()) }
            viewModel { GamePlayViewModel(get()) }
            viewModel { (playerId: Int) -> CreditViewModel(playerId, get()) }
            viewModel { (playerId: Int) -> DebitViewModel(playerId, get()) }
            viewModel { (playerId: Int) -> TransferViewModel(playerId, get()) }
            viewModel { HistoryViewModel(get(), get()) }

            // FORMATTER
            factory { CurrencyValueResolver(get()) }
        }
    }

    private fun initialPlayerCash() = 25000.00
}