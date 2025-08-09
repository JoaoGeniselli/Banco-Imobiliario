package com.dosei.games.toybank.core.data.storage.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("name") val name: String = "",
    @ColumnInfo("color") val colorARGB: Int = 0,
    @ColumnInfo("balance") val balanceInCents: Int = 0,
)