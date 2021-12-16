package com.jgeniselli.banco.infra.memory

import com.jgeniselli.banco.core.GameRules
import com.jgeniselli.banco.core.boundary.GameStorage

class MemoryGameStorage : GameStorage {

    private var initialValue = GameRules.DefaultInitialValue

    override suspend fun saveInitialValue(value: Long) {
        initialValue = value
    }

    override suspend fun fetchInitialValue(): Long {
        return initialValue
    }

    override suspend fun clear() {
        initialValue = GameRules.DefaultInitialValue
    }
}