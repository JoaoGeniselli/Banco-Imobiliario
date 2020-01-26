package com.jgeniselli.banco.core

import org.junit.Assert.*
import org.junit.Test

class ColorHexTest {

    @Test(expected = IllegalArgumentException::class)
    fun `when receive empty hex then break creation`() {
        ColorHex.create("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when receive invalid hex then break creation`() {
        ColorHex.create("$25.000")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when receive incomplete hex then break creation`() {
        ColorHex.create("#F0")
    }

    @Test
    fun `when receive valid hex then create color`() {
        val color = ColorHex.create("#ffaadd")
        assertEquals("#ffaadd", color.hex)
    }
}