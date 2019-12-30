package com.jgeniselli.banco.domain

abstract class UseCase<Input, Output> {
    abstract fun execute(input: Input, onComplete: (Output, Throwable?) -> Unit)
}