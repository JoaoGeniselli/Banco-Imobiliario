package com.jgeniselli.banco.ui.component

import java.text.NumberFormat

class CurrencyValueResolver(
    private val format: NumberFormat = currencyFormat,
) {

    fun format(number: Double): String = format.format(number)

    fun resolve(oldValue: String, newValue: String): Double =
        when {
            hasAdded(oldValue, newValue) -> newValue.parse() * DECIMAL_DIFF
            hasRemoved(oldValue, newValue) -> newValue.parse() / DECIMAL_DIFF
            else -> oldValue.parse()
        }

    private fun String.parse() = format
        .parse(this)
        ?.toDouble()
        ?: throw NumberFormatException("Can't parse $this")

    private fun hasAdded(oldValue: String, newValue: String) = newValue.length > oldValue.length

    private fun hasRemoved(oldValue: String, newValue: String) = newValue.length < oldValue.length

    companion object {
        private const val DECIMAL_DIFF = 10
    }
}
