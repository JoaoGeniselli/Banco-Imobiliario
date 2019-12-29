package com.jgeniselli.banco.game.common.domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "CreditCard"
)
data class DBCreditCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val colorHex: String,
    val name: String
)