package com.jgeniselli.banco.ui.component

import com.jgeniselli.banco.ui.toolbox.CurrencyValueResolver
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.NumberFormat

class CurrencyValueResolverTest {

    lateinit var format: NumberFormat
    lateinit var resolver: CurrencyValueResolver

    @Before
    fun setup() {
        format = mockk()
        resolver = CurrencyValueResolver(format)
    }

    @Test
    fun `test format`() {
        every { format.format(150.0) } returns "$ 150.0"
        val resolver = CurrencyValueResolver(format)

        assertEquals("$ 150.0", resolver.format(150.0))
        verify { format.format(150.0) }
    }

    @Test
    fun `test parse`() {
        every { format.parse("$ 0.0") } returns 0.0
        every { format.parse("$ 0.001") } returns 0.001
        every { format.parse("$ 0.02") } returns 0.02

        assertEquals(0.00, resolver.resolve("$ 0.00", "$ 0.0"), .001)
        verify { format.parse("$ 0.0") }

        assertEquals(0.01, resolver.resolve("$ 0.00", "$ 0.001"), .001)
        verify { format.parse("$ 0.001") }

        assertEquals(0.02, resolver.resolve("$ 0.02", "$ 0.02"), .001)
        verify { format.parse("$ 0.02") }
    }


}