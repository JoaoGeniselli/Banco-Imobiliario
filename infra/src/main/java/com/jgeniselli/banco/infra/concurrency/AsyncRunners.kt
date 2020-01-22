package com.jgeniselli.banco.infra.concurrency

typealias AsyncRunner = (() -> Unit) -> Unit

object AsyncRunners {

    lateinit var io: AsyncRunner
    lateinit var main: AsyncRunner
    lateinit var computation: AsyncRunner

    fun inject(
        ioThreadRunner: AsyncRunner,
        mainThreadRunner: AsyncRunner,
        computationThreadRunner: AsyncRunner
    ) {
        io = ioThreadRunner
        main = mainThreadRunner
        computation = computationThreadRunner
    }
}

fun runInComputation(execution: () -> Unit) = AsyncRunners.computation(execution)

fun runInIO(execution: () -> Unit) = AsyncRunners.io(execution)

fun runInMain(execution: () -> Unit) = AsyncRunners.main(execution)