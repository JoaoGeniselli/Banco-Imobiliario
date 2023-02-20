package com.jgeniselli.banco.infra.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerEntity::class, HistoryLogEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun historyDao(): HistoryDao
}
