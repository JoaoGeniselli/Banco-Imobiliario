package com.jgeniselli.banco.game.common.domain

interface CreditCardRepository {
    fun findAll(onSuccess: (List<CreditCard>) -> Unit, onError: () -> Unit)
}
