package com.jgeniselli.banco

import android.content.Context
import com.jgeniselli.banco.compose.CurrencyValueResolver
import com.jgeniselli.banco.core.ColorHex
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.GameSetup
import com.jgeniselli.banco.core.usecase.HasOngoingGame
import com.jgeniselli.banco.game.common.BRAZIL
import com.jgeniselli.banco.game.play.GameViewModel
import com.jgeniselli.banco.game.transaction.execute.TransactionViewModel
import com.jgeniselli.banco.game.transaction.history.TransactionHistoryViewModel
import com.jgeniselli.banco.home.HomeViewModel
import com.jgeniselli.banco.infra.Infrastructure
import com.jgeniselli.banco.newgame.NewGameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
                    memoryStorage()
                }
            }
            single { get<Infrastructure>().storage }

            // VIEW MODELS
            viewModel { GameViewModel(get(), get()) }
            viewModel { params -> TransactionViewModel(params[0], get()) }
            viewModel { TransactionHistoryViewModel(get(), get()) }

            viewModel { HomeViewModel(HasOngoingGame()) }
            viewModel { NewGameViewModel() }

            // FORMATTER
            factory { DecimalFormat.getCurrencyInstance(BRAZIL) }
            factory { CurrencyValueResolver(get()) }
        }
    }

    private fun initialPlayerCash() = 25000.00

    private fun getAvailableColors(context: Context): List<ColorHex> {
        val colors = context.resources.getStringArray(R.array.available_player_colors)
        return colors.map { ColorHex.create(it) }
    }

}