package com.dosei.games.toybank.toolbox

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

private const val CENTS_TO_REALS_POINTS = 2

@Suppress("DEPRECATION")
val localeBr = Locale("pt", "BR")

private val blrFormatter = NumberFormat.getCurrencyInstance(localeBr)

fun Int.formatBlr(): String = centsToReals().formatBlr()

fun Int.centsToReals(): BigDecimal = BigDecimal(this)
    .movePointLeft(CENTS_TO_REALS_POINTS)

fun BigDecimal.realsToCents(): Int = this
    .movePointRight(CENTS_TO_REALS_POINTS)
    .intValueExact()

fun BigDecimal.formatBlr(): String = blrFormatter.format(this)

