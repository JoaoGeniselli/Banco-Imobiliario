package com.jgeniselli.banco.infra.database

import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.*
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseGameStorageTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    private lateinit var playerDao: PlayerDao
    private lateinit var historyDao: HistoryDao
    private lateinit var storage: DatabaseGameStorage

    @Before
    fun setup() {
        playerDao = mockk(relaxed = true)
        historyDao = mockk(relaxed = true)
        storage = DatabaseGameStorage(playerDao, historyDao, testScope)
    }


}