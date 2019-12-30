package com.jgeniselli.banco.infra.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DBCreditCard::class,
        DBGame::class,
        DBPlayer::class
    ],
    version = 1
)
abstract class Database : RoomDatabase()