package com.dosei.games.toybank.history.data.usecase

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import com.dosei.games.toybank.history.data.mapper.toHistoryEntry
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class LoadHistoryTest {

    @MockK
    lateinit var playerRepository: PlayerRepository

    @MockK
    lateinit var transactionRepository: TransactionRepository

    private lateinit var loadHistory: LoadHistory

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        loadHistory = LoadHistory(playerRepository, transactionRepository)
    }

    @Test
    fun `combine and map valid entries when invoke is called`() = runTest {
        val players = listOf(
            Player(id = 1, name = "1"),
            Player(id = 2, name = "2"),
        )
        val history = listOf(
            TransactionEntity(
                id = 1,
                type = TransactionType.DEPOSIT.id,
                sourcePlayerId = 1,
                amountInCents = 200,
                timestamp = Date().time
            ),
            TransactionEntity(
                id = 2,
                type = 'X', // Invalid
                sourcePlayerId = 1,
                amountInCents = 200,
                timestamp = Date().time
            ),
        )
        every { playerRepository.players } returns flowOf(players)
        every { transactionRepository.history } returns flowOf(history)

        loadHistory().test {
            assertEquals(
                history.mapNotNull { it.toHistoryEntry(players) },
                awaitItem()
            )
            awaitComplete()
        }
    }

}