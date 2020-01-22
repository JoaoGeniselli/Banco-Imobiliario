package com.jgeniselli.banco

import androidx.room.Room
import com.jgeniselli.banco.core.GameAPI
import com.jgeniselli.banco.core.PlayerStorage
import com.jgeniselli.banco.game.common.BRAZIL
import com.jgeniselli.banco.game.play.GameViewModel
import com.jgeniselli.banco.game.transaction.execute.TransactionViewModel
import com.jgeniselli.banco.game.transaction.history.TransactionHistoryViewModel
import com.jgeniselli.banco.infra.db.DBPlayerStorage
import com.jgeniselli.banco.infra.db.Database
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.text.DecimalFormat

object KoinModule {

    val mainModule by lazy {
        module {
            // USE CASE API
            single { GameAPI(get()) }

            // MEMORY STORAGE
//            single<PlayerStorage> { MemoryPlayerStorage() }

            // DATABASE STORAGE
            single {
                Room.databaseBuilder(
                    androidApplication(),
                    Database::class.java,
                    "game_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            single { get<Database>().gameDao() }
            single<PlayerStorage> { DBPlayerStorage(get()) }

            // VIEW MODELS
            viewModel { GameViewModel(get(), get()) }
            viewModel { params -> TransactionViewModel(params[0], get()) }
            viewModel { TransactionHistoryViewModel(get(), get()) }

            // FORMATTER
            single { DecimalFormat.getCurrencyInstance(BRAZIL) }
        }
    }
}