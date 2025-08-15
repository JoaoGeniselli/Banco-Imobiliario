package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.Date

class TransactionRepositoryTest {

    private lateinit var dao: TransactionDao
    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = TransactionRepository(dao)
    }

    @Test
    fun `clear transaction table when clear history is requested`() = runTest {
        repository.clearHistory()
        coVerify { dao.clearAll() }
    }

    @Test
    fun `insert data into dao when add to history is called`() = runTest {
        val entity = TransactionEntity(
            id = 0,
            type = TransactionType.WITHDRAW.id,
            sourcePlayerId = 1,
            amountInCents = 200,
            timestamp = Date().time
        )

        repository.addToHistory(entity)
        coVerify { dao.insertAll(entity) }
    }
}