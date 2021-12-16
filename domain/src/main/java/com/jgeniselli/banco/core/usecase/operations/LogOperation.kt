package com.jgeniselli.banco.core.usecase.operations

import com.jgeniselli.banco.core.Now
import com.jgeniselli.banco.core.Operation
import com.jgeniselli.banco.core.boundary.OperationStorage

class LogOperation(
    private val now: Now,
    private val storage: OperationStorage
) {
    suspend operator fun invoke(operation: Operation) {
        operation.timestamp = now()
        storage.storeOperation(operation)
    }
}