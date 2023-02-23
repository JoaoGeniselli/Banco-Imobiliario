package com.jgeniselli.banco.operations.credit

import com.jgeniselli.banco.core.entity.Player
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.operations.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreditViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var playerId: Int = 1
    private lateinit var gameRepository: GameRepository
    private lateinit var viewModel: CreditViewModel

    @Before
    fun setup() {
        gameRepository = mockk(relaxed = true) {
            val player = Player(1, "john", 1uL, 500.0)
            every { players.value } returns listOf(player)
            every { playerById(playerId) } returns player
        }
        viewModel = CreditViewModel(playerId, gameRepository)
    }

    @Test
    fun `test initial state`() {
        with(viewModel.state.value) {
            assertEquals(0.0, value, .001)
            assertEquals(500.0, balance, .001)
            assertFalse(isDoneEnabled)
            assertFalse(isOperationDone)
        }
        verify { gameRepository.playerById(playerId) }
    }

    @Test
    fun `test update value`() = runBlocking {
        viewModel.updateValue(500.31)

        with(viewModel.state.value) {
            assertEquals(500.31, value, .001)
            assertTrue(isDoneEnabled)
            assertFalse(isOperationDone)
        }
    }

    @Test
    fun `test update value - invalid`() = runBlocking {
        viewModel.updateValue(-10.0)

        with(viewModel.state.value) {
            assertEquals(0.0, value, .001)
            assertFalse(isDoneEnabled)
            assertFalse(isOperationDone)
        }
    }

    @Test
    fun `test shortcut`() = runBlocking {
        with(viewModel.state.value) {
            assertEquals(0.0, value, .001)
            assertFalse(isDoneEnabled)
        }

        viewModel.onShortcut(+100.0)

        with(viewModel.state.value) {
            assertEquals(100.0, value, .001)
            assertTrue(isDoneEnabled)
        }

        viewModel.onShortcut(-50.0)

        with(viewModel.state.value) {
            assertEquals(50.0, value, .001)
            assertTrue(isDoneEnabled)
            assertFalse(isOperationDone)
        }
    }

    @Test
    fun `test shortcut - invalid`() = runBlocking {
        viewModel.onShortcut(-50.0)

        with(viewModel.state.value) {
            assertEquals(0.0, value, .001)
            assertFalse(isDoneEnabled)
        }
    }

    @Test
    fun `test commit operation`() = runBlocking {
        with(viewModel) {
            updateValue(250.5)
            commitOperation()
        }

        coVerify {
            gameRepository.credit(1, 250.5)
        }

        with(viewModel.state.value) {
            assertEquals(250.5, value, .001)
            assertTrue(isDoneEnabled)
            assertTrue(isOperationDone)
        }
    }
}