package com.jgeniselli.banco.core.usecase.operations

import com.jgeniselli.banco.core.Operation
import com.jgeniselli.banco.core.boundary.OperationStorage

class FetchOperationLog(
    private val storage: OperationStorage
) {
    suspend operator fun invoke(): List<Operation> =
        storage.fetchOperations()
}