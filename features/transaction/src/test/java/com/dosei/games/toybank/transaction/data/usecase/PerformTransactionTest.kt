package com.dosei.games.toybank.transaction.data.usecase

import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.test.coAssertThrows
import com.dosei.games.toybank.transaction.TransactionState
import com.dosei.games.toybank.transaction.data.mapper.toDatabaseEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PerformTransactionTest {

    @RelaxedMockK
    lateinit var playerRepository: PlayerRepository

    @RelaxedMockK
    lateinit var transactionRepository: TransactionRepository

    private lateinit var performTransaction: PerformTransaction

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        performTransaction = PerformTransaction(playerRepository, transactionRepository)

        every { playerRepository.players } returns flowOf(
            listOf(
                Player(id = 1, name = "A"),
                Player(id = 2, name = "B"),
                Player(id = 3, name = "C"),
            )
        )
        coEvery { playerRepository.deposit(any(), any()) } returns Player()
        coEvery { playerRepository.withdraw(any(), any()) } returns Player()
        mockkObject(Clock.System)
        every { Clock.System.now().toEpochMilliseconds() } returns 0L
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `throw error when balance is negative`() = runTest {
        val state = TransactionState(
            type = TransactionType.DEPOSIT,
            amountInCents = -1,
            playerId = 1,
        )
        val failure = coAssertThrows<BusinessException> { performTransaction(state) }
        assertEquals(ErrorCode.INVALID_AMOUNT, failure.code)
    }

    @Test
    fun `throw error when balance is zero`() = runTest {
        val state = TransactionState(
            type = TransactionType.DEPOSIT,
            amountInCents = 0,
            playerId = 1,
        )
        val error = coAssertThrows<BusinessException> { performTransaction.invoke(state) }
        assertEquals(ErrorCode.INVALID_AMOUNT, error.code)
    }

    @Test
    fun `perform deposit when state is valid`() = runTest {
        val state = TransactionState(
            type = TransactionType.DEPOSIT,
            amountInCents = 30_00,
            playerId = 1,
        )
        performTransaction(state)

        coVerify { playerRepository.deposit(1, 30_00) }
        coVerify { transactionRepository.addToHistory(state.toDatabaseEntity()) }
    }

    @Test
    fun `perform withdraw when state is valid`() = runTest {
        val state = TransactionState(
            type = TransactionType.WITHDRAW,
            amountInCents = 30_00,
            playerId = 1,
        )
        performTransaction(state)

        coVerify { playerRepository.withdraw(1, 30_00) }
        coVerify { transactionRepository.addToHistory(state.toDatabaseEntity()) }
    }

    @Test
    fun `perform transfer when state is valid`() = runTest {
        val state = TransactionState(
            type = TransactionType.TRANSFER,
            amountInCents = 30_00,
            playerId = 1,
            destinationPlayerId = 3
        )
        performTransaction(state)

        coVerify { playerRepository.withdraw(1, 30_00) }
        coVerify { playerRepository.deposit(3, 30_00) }
        coVerify { transactionRepository.addToHistory(state.toDatabaseEntity()) }
    }

    @Test
    fun `throw error when transfer destination player is null`() = runTest {
        val state = TransactionState(
            type = TransactionType.TRANSFER,
            amountInCents = 30_00,
            playerId = 1,
            destinationPlayerId = null
        )
        val error = coAssertThrows<BusinessException> { performTransaction(state) }
        assertEquals(ErrorCode.INVALID_DESTINATION_PLAYER, error.code)
    }
}