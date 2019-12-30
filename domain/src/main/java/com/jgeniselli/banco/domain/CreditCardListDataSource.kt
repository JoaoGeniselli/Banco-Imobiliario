package com.jgeniselli.banco.domain

interface CreditCardListDataSource {
    fun findAll(onSuccess: (List<CreditCard>) -> Unit, onError: () -> Unit)
}