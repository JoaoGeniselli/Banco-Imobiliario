package com.jgeniselli.banco.infra.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Game"
)
data class DBGame(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)