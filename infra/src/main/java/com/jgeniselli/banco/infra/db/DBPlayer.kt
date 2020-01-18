package com.jgeniselli.banco.infra.db

import androidx.room.*

@Entity(
    tableName = "Player"
)
data class DBPlayer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val color: String,
    var cash: Double
)