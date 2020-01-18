package com.jgeniselli.banco.infra.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DBPlayer::class,
        DBTransaction::class
    ],
    version = 3
)
abstract class Database : RoomDatabase() {

    abstract fun gameDao(): DBGameDao
}