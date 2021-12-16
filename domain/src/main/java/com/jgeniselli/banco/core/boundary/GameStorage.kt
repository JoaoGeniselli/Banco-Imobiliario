package com.jgeniselli.banco.core.boundary

interface GameStorage {
    suspend fun saveInitialValue(value: Long)
    suspend fun fetchInitialValue(): Long
    suspend fun clear()
}