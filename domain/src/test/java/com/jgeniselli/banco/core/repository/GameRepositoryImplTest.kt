package com.jgeniselli.banco.core.repository

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameRepositoryImplTest {

    private lateinit var storage: GameStorage
    private lateinit var repository: GameRepositoryImpl

    @Before
    fun setup() {
        storage = mockk(relaxed = true)
        repository = GameRepositoryImpl(storage)
    }

    @Test
    fun `test start game`() = runBlocking {
        val players = listOf(
            "test" to 0uL
        )
        repository.startGame(players)

        coVerify {
            storage.clearHistory()
            storage.clearPlayerList()
            storage.addPlayers(players, 25_000.0)
        }
    }

    @Test
    fun `test credit`() = runBlocking {
        val slot = slot<OperationLog>()
        val player = Player(1, "john", 1uL, 250.0)
        every { storage.players.value } returns listOf(player)

        repository.credit(1, 250.0)

        coVerify {
            storage.updateBalance(1, 500.0)
            storage.addToHistory(capture(slot))
        }

        assertTrue(slot.captured is OperationLog.Credit)
        with(slot.captured as OperationLog.Credit) {
            assertEquals(player, this.player)
            assertEquals(250.0, this.value, .0001)
        }
    }

    @Test
    fun `test debit`() = runBlocking {
        val slot = slot<OperationLog>()
        val player = Player(1, "john", 1uL, 700.0)
        every { storage.players.value } returns listOf(player)

        repository.debit(1, 300.0)

        coVerify {
            storage.updateBalance(1, 400.0)
            storage.addToHistory(capture(slot))
        }

        assertTrue(slot.captured is OperationLog.Debit)
        with(slot.captured as OperationLog.Debit) {
            assertEquals(player, this.player)
            assertEquals(300.0, this.value, .0001)
        }
    }

    @Test
    fun `test transfer`() = runBlocking {
        val slot = slot<OperationLog>()
        val player1 = Player(1, "john", 1uL, 630.25)
        val player2 = Player(2, "william", 2uL, 969.75)
        every { storage.players.value } returns listOf(player1, player2)

        repository.transfer(1, 2, 30.25)

        coVerify {
            storage.updateBalance(1, 600.0)
            storage.updateBalance(2, 1000.0)
            storage.addToHistory(capture(slot))
        }

        assertTrue(slot.captured is OperationLog.Transfer)
        with(slot.captured as OperationLog.Transfer) {
            assertEquals(player1, this.source)
            assertEquals(player2, this.recipient)
            assertEquals(30.25, this.value, .0001)
        }
    }

    @Test
    fun `test delegation`() = runBlocking {
        val playerList = listOf(
            Player(1, "john", 1uL, 630.25)
        )
        val historyList = listOf(
            OperationLog.Credit(playerList.first(), 350.0)
        )

        every { storage.players.value } returns playerList
        every { storage.history.value } returns historyList

        assertEquals(playerList, repository.players.value)
        assertEquals(historyList, repository.history.value)
    }
}