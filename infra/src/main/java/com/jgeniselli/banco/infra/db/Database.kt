package com.jgeniselli.banco.infra.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DBPlayer::class
    ],
    version = 2
)
abstract class Database : RoomDatabase() {

    abstract fun gameDao(): DBGameDao
}