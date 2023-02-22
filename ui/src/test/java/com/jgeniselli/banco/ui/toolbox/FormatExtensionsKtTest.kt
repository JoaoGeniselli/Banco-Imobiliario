package com.jgeniselli.banco.ui.toolbox

import org.junit.Assert.*
import org.junit.Test
import java.text.DecimalFormat

class FormatExtensionsKtTest {

    @Test
    fun `test extensions`() {
        val systemFormat = DecimalFormat.getCurrencyInstance(BRAZIL)
        assertEquals(systemFormat, currencyFormat)
        assertEquals(systemFormat.format(250.0), 250.0.toCurrency())
        assertEquals("pt", BRAZIL.language)
        assertEquals("BR", BRAZIL.country)
    }
}