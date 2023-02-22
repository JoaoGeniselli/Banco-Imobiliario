package com.jgeniselli.banco.operations.transfer

import androidx.compose.ui.graphics.Color
import com.jgeniselli.banco.core.entities.Player
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.operations.MainDispatcherRule
import com.jgeniselli.banco.ui.component.PlayerSummary
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransferViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val playerId: Int = 2
    private lateinit var gameRepository: GameRepository
    private lateinit var viewModel: TransferViewModel

    @Before
    fun before() {
        gameRepository = mockk(relaxed = true) {
            val player1 = Player(1, "p1", Color.Red.value, 100.0)
            val player2 = Player(2, "p2", Color.Green.value, 200.0)
            val player3 = Player(3, "p3", Color.Blue.value, 300.0)
            every { players.value } returns listOf(player1, player2, player3)
            every { playerById(playerId) } returns player2
        }
        viewModel = TransferViewModel(playerId, gameRepository)
    }

    @Test
    fun `test initial state`() = runTest {
        with(viewModel.uiState.first()) {
            assertEquals(200.0, balance, .001)
            assertEquals(0.0, transferValue, .001)
            assertFalse(isDoneAvailable)
            assertFalse(isOperationDone)
            assertEquals(2, availableRecipients.size)
            assertEquals(
                PlayerSummary("p1", Color.Red, false),
                availableRecipients[0]
            )
            assertEquals(
                PlayerSummary("p3", Color.Blue, false),
                availableRecipients[1]
            )
        }

        verify {
            gameRepository.playerById(playerId)
        }
    }

    @Test
    fun `test update value`() = runTest {
        viewModel.updateValue(100.0)
        with(viewModel.uiState.first()) {
            assertEquals(100.0, transferValue, .001)
            assertFalse(isDoneAvailable)
            assertFalse(isOperationDone)
        }
    }

    @Test
    fun `test select recipient and enable done`() = runTest {
        with(viewModel) {
            updateValue(250.0)
            selectRecipientAt(1)
        }
        with(viewModel.uiState.first()) {
            assertEquals(250.0, transferValue, .001)
            assertTrue(isDoneAvailable)
            assertFalse(isOperationDone)
            assertEquals(
                PlayerSummary("p1", Color.Red, false),
                availableRecipients[0]
            )
            assertEquals(
                PlayerSummary("p3", Color.Blue, true),
                availableRecipients[1]
            )
        }
    }

    @Test
    fun `test shortcut`() = runTest {
        with(viewModel) {
            selectRecipientAt(1)
            updateValue(100.0)
            onShortcut(300.0)
        }
        with(viewModel.uiState.first()) {
            assertEquals(400.0, transferValue, .001)
            assertTrue(isDoneAvailable)
            assertFalse(isOperationDone)
        }
    }

    @Test
    fun `test done`() = runTest {
        with(viewModel) {
            updateValue(250.0)
            selectRecipientAt(1)
            onDone()
        }

        coVerify { gameRepository.transfer(2, 3, 250.0) }

        with(viewModel.uiState.first()) {
            assertTrue(isOperationDone)
        }
    }
}