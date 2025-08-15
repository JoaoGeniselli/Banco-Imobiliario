package com.dosei.games.toybank.core.data.model

enum class TransactionType(val id: Char) {
    DEPOSIT('D'),
    WITHDRAW('W'),
    TRANSFER('T'),
}