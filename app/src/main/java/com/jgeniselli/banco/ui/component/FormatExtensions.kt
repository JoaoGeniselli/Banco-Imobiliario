package com.jgeniselli.banco.ui.component

import com.jgeniselli.banco.game.common.BRAZIL
import java.text.DecimalFormat
import java.text.NumberFormat

val currencyFormat: NumberFormat get() = DecimalFormat.getCurrencyInstance(BRAZIL)

fun Double.toCurrency(): String = currencyFormat.format(this)