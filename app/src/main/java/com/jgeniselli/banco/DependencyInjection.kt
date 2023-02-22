package com.jgeniselli.banco

import com.jgeniselli.banco.core.CoreModule
import com.jgeniselli.banco.core.usecase.HasOngoingGame
import com.jgeniselli.banco.game.history.HistoryViewModel
import com.jgeniselli.banco.game.history.OperationFormatter
import com.jgeniselli.banco.game.play.GamePlayViewModel
import com.jgeniselli.banco.home.HomeViewModel
import com.jgeniselli.banco.infra.database.InfraModule
import com.jgeniselli.banco.newgame.NewGameViewModel
import com.jgeniselli.banco.operations.credit.CreditViewModel
import com.jgeniselli.banco.operations.debit.DebitViewModel
import com.jgeniselli.banco.operations.transfer.TransferViewModel
import com.jgeniselli.banco.ui.toolbox.CurrencyValueResolver
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyInjection {

    val mainModule by lazy {
        module {
            //region Core
            single { CoreModule.gameRepository(get()) }
            factory { HasOngoingGame(get()) }
            //endregion

            //region Infra
            single { InfraModule.databaseGameStorage() }
            //endregion

            //region View Model
            viewModel { HomeViewModel(get()) }
            viewModel { NewGameViewModel(get()) }
            viewModel { GamePlayViewModel(get()) }
            viewModel { (playerId: Int) -> CreditViewModel(playerId, get()) }
            viewModel { (playerId: Int) -> DebitViewModel(playerId, get()) }
            viewModel { (playerId: Int) -> TransferViewModel(playerId, get()) }
            viewModel { HistoryViewModel(get(), get()) }
            //endregion

            //region Utility
            factory { CurrencyValueResolver(get()) }
            factory { OperationFormatter(androidContext()) }
            //endregion
        }
    }
}