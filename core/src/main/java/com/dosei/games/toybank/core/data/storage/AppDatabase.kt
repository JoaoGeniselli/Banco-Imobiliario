package com.dosei.games.toybank.core.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity

@Database(entities = [Player::class, TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun transactionDao(): TransactionDao
}