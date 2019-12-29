package com.jgeniselli.banco.game.common.domain

data class Color(
    val id: Long,
    val colorHex: String,
    val name: String
) {

    companion object {

        fun black() = Color(0, "#000000", "Preto")
    }
}
