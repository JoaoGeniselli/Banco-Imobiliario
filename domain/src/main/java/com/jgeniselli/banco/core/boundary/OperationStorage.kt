package com.jgeniselli.banco.core.boundary

import com.jgeniselli.banco.core.Operation

interface OperationStorage {
    suspend fun storeOperation(operation: Operation)
    suspend fun fetchOperations(): List<Operation>
    suspend fun clear()
}