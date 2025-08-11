package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.storage.transaction.TransactionDao
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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
}