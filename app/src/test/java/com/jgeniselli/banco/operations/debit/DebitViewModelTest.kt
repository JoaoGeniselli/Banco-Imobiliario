package com.jgeniselli.banco.operations.debit

import com.jgeniselli.banco.core.entities.Player
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.operations.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DebitViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var playerId: Int = 1
    private lateinit var gameRepository: GameRepository
    private lateinit var viewModel: DebitViewModel

    @Before
    fun setup() {
        gameRepository = mockk(relaxed = true) {
            every { players.value } returns listOf(
                Player(1, "john", 1uL, 500.0)
            )
        }
        viewModel = DebitViewModel(playerId, gameRepository)
    }

    @Test
    fun `test update value`() = runBlocking {
        with(viewModel.state.value) {
            assertEquals(0.0, value, .001)
            assertFalse(isDoneEnabled)
        }

        viewModel.updateValue(500.31)

        with(viewModel.state.value) {
            assertEquals(500.31, value, .001)
            assertTrue(isDoneEnabled)
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
    fun `test commit operation`() = runBlocking {
        with(viewModel) {
            updateValue(250.0)
            commitOperation()
        }

        coVerify {
            gameRepository.debit(1, 250.0)
        }

        with(viewModel.state.value) {
            assertEquals(250.0, value, .001)
            assertTrue(isDoneEnabled)
            assertTrue(isOperationDone)
        }
    }
}