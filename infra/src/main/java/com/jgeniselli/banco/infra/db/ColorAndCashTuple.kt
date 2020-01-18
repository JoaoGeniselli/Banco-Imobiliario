package com.jgeniselli.banco.infra.db

import androidx.room.ColumnInfo

data class ColorAndCashTuple(
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "cash")
    val cash: Double
)