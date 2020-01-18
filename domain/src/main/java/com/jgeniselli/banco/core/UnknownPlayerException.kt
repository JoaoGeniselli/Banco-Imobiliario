package com.jgeniselli.banco.core

class UnknownPlayerException(private val id: Long) : RuntimeException() {
    override val message: String?
        get() = "Player with id: $id is unknown"
}