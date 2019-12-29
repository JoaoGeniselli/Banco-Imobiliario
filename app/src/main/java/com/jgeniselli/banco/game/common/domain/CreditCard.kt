package com.jgeniselli.banco.game.common.domain

data class CreditCard(
    val id: Long,
    val colorHex: String,
    val name: String
) {

    companion object {

        fun black() = CreditCard(0, "#000000", "Preto")
    }
}