package com.jgeniselli.banco.infra.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Transaction",
    foreignKeys = [
        ForeignKey(entity = DBPlayer::class, parentColumns = ["id"], childColumns = ["player_id"])
    ]
)
data class DBTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "player_id")
    val playerId: Long,
    val cash: Double
)