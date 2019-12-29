package com.jgeniselli.banco.game.common.domain.db

import androidx.room.*

@Entity(
    tableName = "Player",
    foreignKeys = [
        ForeignKey(entity = DBGame::class, parentColumns = ["id"], childColumns = ["game_id"])
    ]
)
data class DBPlayer(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Embedded
    val creditCard: DBCreditCard?,
    @ColumnInfo(name = "game_id")
    val gameId: Long,
    var cash: Double
)