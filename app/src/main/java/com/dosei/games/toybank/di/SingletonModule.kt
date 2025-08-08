package com.dosei.games.toybank.di

import android.content.Context
import androidx.room.Room
import com.dosei.games.toybank.data.local.storage.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class, ViewModelComponent::class, ActivityComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "bank_database")
            .build()

    @Provides
    @Singleton
    fun providesPlayerDao(database: AppDatabase) = database.playerDao()

    @Provides
    @Singleton
    fun providesTransactionDao(database: AppDatabase) = database.transactionDao()
}