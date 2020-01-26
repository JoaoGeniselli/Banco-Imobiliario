package com.jgeniselli.banco.core

import java.util.regex.Pattern

class ColorHex private constructor(val hex: String) {

    companion object {

        private const val COLOR_HEX_REGEX = "#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})"

        fun create(hex: String): ColorHex {
            validateHex(hex)
            return ColorHex(hex)
        }

        private fun validateHex(hex: String) {
            val pattern = Pattern.compile(COLOR_HEX_REGEX)
            val matcher = pattern.matcher(hex)
            if (!matcher.matches()) {
                throw IllegalArgumentException("Invalid Hex color")
            }
        }
    }
}