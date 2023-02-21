package com.jgeniselli.banco.infra

import com.jgeniselli.banco.core.entities.OperationLog
import com.jgeniselli.banco.core.entities.Player
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MemoryGameStorageTest {

    private lateinit var storage: MemoryGameStorage


    @Before
    fun setup() {
        storage = MemoryGameStorage()
    }

    @Test
    fun `test add and clear players`() = runBlocking {
        initGame()

        assertEquals(
            Player(1, "john", 1uL, 1000.0),
            storage.players.value[0]
        )
        assertEquals(
            Player(2, "william", 2uL, 1000.0),
            storage.players.value[1]
        )

        storage.clearPlayerList()

        assertTrue(storage.players.value.isEmpty())
    }

    private suspend fun initGame() {
        val players = listOf(
            "john" to 1uL,
            "william" to 2uL
        )
        storage.addPlayers(players, 1000.0)
    }

    @Test
    fun `test update balance`() = runBlocking {
        initGame()

        storage.updateBalance(1, 500.0)
        storage.updateBalance(2, 1250.0)

        assertEquals(
            Player(1, "john", 1uL, 500.0),
            storage.players.value[0]
        )
        assertEquals(
            Player(2, "william", 2uL, 1250.0),
            storage.players.value[1]
        )
    }

    @Test
    fun `test history`() = runBlocking {
        initGame()

        val log1 = OperationLog.Credit(storage.players.value[0], 300.0)
        val log2 = OperationLog.Debit(storage.players.value[1], 250.0)

        storage.addToHistory(log1)
        storage.addToHistory(log2)

        assertEquals(log1, storage.history.value[0])
        assertEquals(log2, storage.history.value[1])

        storage.clearHistory()

        assertTrue(storage.history.value.isEmpty())
    }

    @Test
    fun `test ongoing game`() = runBlocking {
        assertFalse(storage.isOngoingGameAvailable())

        initGame()

        assertTrue(storage.isOngoingGameAvailable())
    }

}