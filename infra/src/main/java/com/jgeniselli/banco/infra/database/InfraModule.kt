package com.jgeniselli.banco.infra.database

import android.content.Context
import androidx.room.Room
import com.jgeniselli.banco.core.repository.GameStorage
import com.jgeniselli.banco.infra.MemoryGameStorage

object InfraModule {

    lateinit var database: GameDatabase

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "game-database"
        ).build()
    }

    fun databaseGameStorage(): GameStorage = DatabaseGameStorage(
        playerDao = database.playerDao(),
        historyDao = database.historyDao()
    )

    fun memoryGameStorage(): GameStorage = MemoryGameStorage()
}