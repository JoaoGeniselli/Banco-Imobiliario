package com.dosei.games.toybank.di

import com.dosei.games.toybank.core.data.storage.AppDatabase
import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesPlayerDao(database: AppDatabase): PlayerDao = database.playerDao()

    @Provides
    fun providesTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao()
}