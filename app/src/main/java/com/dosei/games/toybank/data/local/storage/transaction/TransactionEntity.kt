package com.dosei.games.toybank.data.local.storage.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("type") val type: Char,
    @ColumnInfo("source_player_id") val sourcePlayerId: Int,
    @ColumnInfo("amount") val amountInCents: Int,
    @ColumnInfo("timestamp") val timestamp: Long,
    @ColumnInfo("destination_player_id") val destinationPlayerId: Int? = null,
)