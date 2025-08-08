package com.dosei.games.toybank.data.local.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dosei.games.toybank.data.local.storage.player.PlayerDao
import com.dosei.games.toybank.data.local.storage.player.PlayerEntity
import com.dosei.games.toybank.data.local.storage.transaction.TransactionDao
import com.dosei.games.toybank.data.local.storage.transaction.TransactionEntity

@Database(entities = [PlayerEntity::class, TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun transactionDao(): TransactionDao
}