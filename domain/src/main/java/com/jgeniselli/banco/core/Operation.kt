package com.jgeniselli.banco.core

import java.util.*

sealed class Operation {

    lateinit var timestamp: Date

    class Credit(
        val playerId: Int,
        val value: Long
    ) : Operation()

    class Debit(
        val playerId: Int,
        val value: Long
    ) : Operation()

    class Transfer(
        val senderId: Int,
        val receiverId: Int,
        val creditValue: Long
    ) : Operation()
}