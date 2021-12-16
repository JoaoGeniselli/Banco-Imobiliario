package com.jgeniselli.banco.infra.memory

import com.jgeniselli.banco.core.Operation
import com.jgeniselli.banco.core.boundary.OperationStorage

class MemoryOperationStorage : OperationStorage {

    private val operations = mutableListOf<Operation>()

    override suspend fun storeOperation(operation: Operation) {
        operations.add(operation)
    }

    override suspend fun fetchOperations(): List<Operation> {
        return operations.toList()
    }

    override suspend fun clear() {
        operations.clear()
    }
}