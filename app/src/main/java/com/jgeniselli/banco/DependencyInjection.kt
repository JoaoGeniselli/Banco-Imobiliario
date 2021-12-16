package com.jgeniselli.banco

import android.content.Context
import com.jgeniselli.banco.core.ColorHex
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.GameSetup
import com.jgeniselli.banco.core.Now
import com.jgeniselli.banco.core.boundary.GameStorage
import com.jgeniselli.banco.core.boundary.NewPlayerStorage
import com.jgeniselli.banco.core.boundary.OperationStorage
import com.jgeniselli.banco.core.usecase.game.CheckWinner
import com.jgeniselli.banco.core.usecase.game.FinishGame
import com.jgeniselli.banco.core.usecase.game.IsGameOngoing
import com.jgeniselli.banco.core.usecase.game.StartGame
import com.jgeniselli.banco.core.usecase.operations.*
import com.jgeniselli.banco.core.usecase.players.FetchPlayerById
import com.jgeniselli.banco.core.usecase.players.FetchPlayers
import com.jgeniselli.banco.game.common.BRAZIL
import com.jgeniselli.banco.game.create.CreateGameViewModel
import com.jgeniselli.banco.game.play.GameViewModel
import com.jgeniselli.banco.game.play.StartupViewModel
import com.jgeniselli.banco.game.transaction.execute.TransactionViewModel
import com.jgeniselli.banco.game.transaction.history.TransactionHistoryViewModel
import com.jgeniselli.banco.infra.Infrastructure
import com.jgeniselli.banco.infra.memory.MemoryGameStorage
import com.jgeniselli.banco.infra.memory.MemoryNewPlayerStorage
import com.jgeniselli.banco.infra.memory.MemoryOperationStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.text.DecimalFormat

object DependencyInjection {

    val mainModule by lazy {
        module {
            // USE CASE API
            single { GameAPI(get(), get()) }
            factory { GameSetup(initialPlayerCash(), getAvailableColors(get())) }

            // INFRASTRUCTURE
            single {
                Infrastructure.create {
                    coroutinesConcurrency()
                    databaseStorage(androidApplication())
                }
            }
            single { get<Infrastructure>().storage }

            // VIEW MODELS
            viewModel { GameViewModel(get(), get()) }
            viewModel { params -> TransactionViewModel(params[0], get()) }
            viewModel { TransactionHistoryViewModel(get(), get()) }
            viewModel { StartupViewModel(get()) }
            viewModel { CreateGameViewModel(get()) }

            // FORMATTER
            single { DecimalFormat.getCurrencyInstance(BRAZIL) }

            // region INFRA
            single<NewPlayerStorage> { MemoryNewPlayerStorage() }
            single<GameStorage> { MemoryGameStorage() }
            single<OperationStorage> { MemoryOperationStorage() }
            // endregion

            // region DOMAIN
            single { CheckWinner(get()) }
            single { FinishGame(get(), get(), get()) }
            single { StartGame(get(), get()) }
            single { Credit(get(), get(), get()) }
            single { Debit(get(), get(), get()) }
            single { Transfer(get(), get(), get()) }
            single { FetchOperationLog(get()) }
            single { LogOperation(get(), get()) }
            single { Now() }
            single { FetchPlayerById(get()) }
            single { FetchPlayers(get()) }
            single { IsGameOngoing(get()) }
            // endregion
        }
    }

    private fun initialPlayerCash() = 25000.00

    private fun getAvailableColors(context: Context): List<ColorHex> {
        val colors = context.resources.getStringArray(R.array.available_player_colors)
        return colors.map { ColorHex.create(it) }
    }

}