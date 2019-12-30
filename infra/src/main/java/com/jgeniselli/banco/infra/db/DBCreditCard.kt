package com.jgeniselli.banco.infra.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "CreditCard"
)
data class DBCreditCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "credit_card_id")
    val id: Long,
    val colorHex: String,
    val name: String
)