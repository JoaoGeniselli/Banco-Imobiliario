package com.dosei.games.toybank.core.toolbox

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class CurrencyUtilsTest {

    @Test
    fun `check cents to BLR formatter`() {
        //   == no line break space
        assertEquals("R\$ 10,00", 1000.formatBlr())
    }

    @Test
    fun `check big decimal BLR formatter`() {
        //   == no line break space
        assertEquals("R\$ 10,00", BigDecimal.valueOf(10).formatBlr())
    }

    @Test
    fun `check cents to reals conversion`() {
        assertEquals(
            BigDecimal.valueOf(12.34),
            1234.centsToReals()
        )
    }

    @Test
    fun `check reals to cents conversion`() {
        assertEquals(
            1250,
            BigDecimal.valueOf(12.50).realsToCents()
        )
    }




}