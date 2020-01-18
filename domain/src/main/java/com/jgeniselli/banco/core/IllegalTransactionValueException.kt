package com.jgeniselli.banco.core

class IllegalTransactionValueException private constructor(message: String) : RuntimeException(message) {

    companion object {
        fun mustBePositive(): IllegalTransactionValueException {
            return IllegalTransactionValueException("This transaction value must be > 0.0")
        }
    }
}