package com.jgeniselli.banco.game.common

import java.text.DecimalFormat

class Currency {

    private val formatter = DecimalFormat.getCurrencyInstance(BRAZIL)

    fun format(value: Long): String = formatter.format(value)

    fun parse(value: String): Long? = formatter.parse(value)?.toLong()
}