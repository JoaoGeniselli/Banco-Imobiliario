package com.jgeniselli.banco.infra.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val LOG_TYPE_CREDIT = "C"
const val LOG_TYPE_DEBIT = "D"
const val LOG_TYPE_TRANSFER = "T"

@Entity(tableName = "history")
data class HistoryLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "source_id") val sourceId: Int?,
    @ColumnInfo(name = "recipient_id") val recipientId: Int?,
    @ColumnInfo(name = "value") val value: Double?,
)
