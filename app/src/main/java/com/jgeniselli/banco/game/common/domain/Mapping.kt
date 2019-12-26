package com.jgeniselli.banco.game.common.domain

interface Mapper<Input, Output> {
    fun map(input: Input) : Output
}

interface ListMapper<Input, Output>: Mapper<List<Input>, List<Output>>
