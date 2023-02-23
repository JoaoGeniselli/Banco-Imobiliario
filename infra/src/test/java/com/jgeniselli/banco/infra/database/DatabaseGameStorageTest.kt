package com.jgeniselli.banco.infra.database

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseGameStorageTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var playerDao: PlayerDao
    private lateinit var historyDao: HistoryDao
    private lateinit var storage: DatabaseGameStorage

    private lateinit var players: MutableStateFlow<List<PlayerEntity>>
    private lateinit var history: MutableStateFlow<List<HistoryWithPlayers>>

    @Before
    fun setup() {
        players = MutableStateFlow(emptyList())
        history = MutableStateFlow(emptyList())

        playerDao = mockk(relaxed = true) {
            every { getAll() } returns players
        }
        historyDao = mockk(relaxed = true) {
            every { getAllWithPlayers() } returns history
        }
        storage = DatabaseGameStorage(
            playerDao,
            historyDao,
            TestScope(mainDispatcherRule.testDispatcher)
        )
    }

    @Test
    fun `test players`() {
        players.value = listOf(
            PlayerEntity(1, "", 1L, 10.0),
            PlayerEntity(1, "2", 2L, 20.0),
        )

        assertEquals(
            listOf(
                Player(1, "", 1uL, 10.0),
                Player(1, "2", 2uL, 20.0),
            ),
            storage.players.value
        )
    }

    @Test
    fun `test add players`() = runTest {
        val namesAndColors = listOf(
            "john" to 1uL,
            "william" to 2uL
        )
        storage.addPlayers(namesAndColors, 450.0)

        coVerify {
            playerDao.insertAll(
                listOf(
                    PlayerEntity(name = "john", color = 1L, balance = 450.0),
                    PlayerEntity(name = "william", color = 2L, balance = 450.0),
                )
            )
        }
    }

    @Test
    fun `test history`() = runTest {
        val op1 = HistoryLogEntity(1, LOG_TYPE_CREDIT, 1, null, 200.0)
        val op2 = HistoryLogEntity(2, LOG_TYPE_TRANSFER, 1, 2, 100.0)
        val player1 = PlayerEntity(1, "p1", 1L, 10.0)
        val player2 = PlayerEntity(2, "p2", 1L, 20.0)

        val entity1 = HistoryWithPlayers(op1, player1, null)
        val entity2 = HistoryWithPlayers(op2, player1, player2)
        history.value = listOf(entity1, entity2)

        assertEquals(
            listOf(
                entity1.toDomainOperation(),
                entity2.toDomainOperation(),
            ),
            storage.history.first()
        )
    }

    @Test
    fun `test is ongoing game available`() = runTest {
        assertFalse(storage.isOngoingGameAvailable())

        players.value = listOf(PlayerEntity(1, "", 1L, 0.0))
        assertTrue(storage.isOngoingGameAvailable())
    }

    @Test
    fun `test update balance`() = runTest {
        val player = PlayerEntity(1, "x", 1L, 250.0)
        coEvery { playerDao.getById(any()) } returns player

        storage.updateBalance(player.id, 350.0)

        coVerify {
            playerDao.getById(1)
            playerDao.update(player.copy(balance = 350.0))
        }
    }

    @Test
    fun `test add to history`() = runTest {
        val operation = OperationLog.Credit(
            player = Player(1, "", 1uL, 0.0),
            value = 400.0
        )
        storage.addToHistory(operation)

        coVerify { historyDao.insertAll(operation.toEntity()) }
    }

    @Test
    fun `test update balance - unknown player`() = runTest {
        coEvery { playerDao.getById(any()) } returns null

        storage.updateBalance(35, 350.0)

        coVerify { playerDao.getById(35) }
        coVerify(exactly = 0) { playerDao.update(any()) }
    }

    @Test
    fun `test clear history`() = runTest {
        storage.clearHistory()
        coVerify { historyDao.deleteAll() }
    }

    @Test
    fun `test clear player list`() = runTest {
        storage.clearPlayerList()
        coVerify { playerDao.deleteAll() }
    }
}