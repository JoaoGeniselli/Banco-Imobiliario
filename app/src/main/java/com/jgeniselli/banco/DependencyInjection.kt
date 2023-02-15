package com.jgeniselli.banco

import android.content.Context
import com.jgeniselli.banco.compose.GameRepository
import com.jgeniselli.banco.compose.MemoryGameRepository
import com.jgeniselli.banco.core.ColorHex
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.GameSetup
import com.jgeniselli.banco.core.usecase.HasOngoingGame
import com.jgeniselli.banco.game.play.GamePlayViewModel
import com.jgeniselli.banco.game.play.GameViewModel
import com.jgeniselli.banco.game.transaction.execute.TransactionViewModel
import com.jgeniselli.banco.game.transaction.history.TransactionHistoryViewModel
import com.jgeniselli.banco.home.HomeViewModel
import com.jgeniselli.banco.infra.Infrastructure
import com.jgeniselli.banco.newgame.NewGameViewModel
import com.jgeniselli.banco.operations.credit.CreditViewModel
import com.jgeniselli.banco.ui.component.CurrencyValueResolver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
                    memoryStorage()
                }
            }
            single { get<Infrastructure>().storage }
            single<GameRepository> { MemoryGameRepository() }

            // VIEW MODELS
            viewModel { GameViewModel(get(), get()) }
            viewModel { params -> TransactionViewModel(params[0], get()) }
            viewModel { TransactionHistoryViewModel(get(), get()) }

            viewModel { HomeViewModel(HasOngoingGame()) }
            viewModel { NewGameViewModel() }
            viewModel { GamePlayViewModel(get()) }
            viewModel { (playerId: Int) ->
                CreditViewModel(playerId, get())
            }

            // FORMATTER
            factory { CurrencyValueResolver(get()) }
        }
    }

    private fun initialPlayerCash() = 25000.00

    private fun getAvailableColors(context: Context): List<ColorHex> {
        val colors = context.resources.getStringArray(R.array.available_player_colors)
        return colors.map { ColorHex.create(it) }
    }

}