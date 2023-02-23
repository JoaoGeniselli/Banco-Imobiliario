package com.jgeniselli.banco.newgame

import com.jgeniselli.banco.core.repository.DEFAULT_INITIAL_BALANCE
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.operations.MainDispatcherRule
import com.jgeniselli.banco.ui.component.PlayerSummary
import com.jgeniselli.banco.ui.theme.*
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewGameViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: GameRepository
    private lateinit var viewModel: NewGameViewModel

    @Before
    fun before() {
        repository = mockk(relaxed = true)
        viewModel = NewGameViewModel(repository)
    }

    @Test
    fun `test initial state`() = runTest {
        with(viewModel.uiState.value) {
            assertFalse(isGameStarted)
            assertFalse(isGameStartAllowed)
            assertTrue(canAddNewPlayer)
            assertTrue(players.isEmpty())
        }
    }

    @Test
    fun `test add single player`() = runTest {
        viewModel.onAddNewPlayer("john")

        with(viewModel.uiState.value) {
            assertFalse(isGameStarted)
            assertFalse(isGameStartAllowed)
            assertTrue(canAddNewPlayer)

            assertEquals(1, players.size)
            assertEquals(
                PlayerSummary("john", PlayerRed, false),
                players[0]
            )
        }
    }

    @Test
    fun `test add all players`() {
        (1..6).forEach {
            viewModel.onAddNewPlayer(it.toString())
        }

        with(viewModel.uiState.value) {
            assertFalse(isGameStarted)
            assertFalse(canAddNewPlayer)
            assertTrue(isGameStartAllowed)

            assertEquals(6, players.size)
            assertEquals(PlayerSummary("1", PlayerRed, false), players[0])
            assertEquals(PlayerSummary("2", PlayerBlue, false), players[1])
            assertEquals(PlayerSummary("3", PlayerGreen, false), players[2])
            assertEquals(PlayerSummary("4", PlayerYellow, false), players[3])
            assertEquals(PlayerSummary("5", PlayerGray, false), players[4])
            assertEquals(PlayerSummary("6", PlayerPurple, false), players[5])
        }
    }

    @Test
    fun `test start game`() {
        viewModel.run {
            onAddNewPlayer("1")
            onAddNewPlayer("2")
            onStartGame()
        }

        coVerify {
            repository.startGame(
                viewModel.uiState.value.players.map { it.name to it.color.value },
                DEFAULT_INITIAL_BALANCE
            )
        }

        with(viewModel.uiState.value) {
            assertTrue(canAddNewPlayer)
            assertTrue(isGameStartAllowed)
            assertTrue(isGameStarted)

            assertEquals(2, players.size)
            assertEquals(PlayerSummary("1", PlayerRed, false), players[0])
            assertEquals(PlayerSummary("2", PlayerBlue, false), players[1])
        }
    }

    @Test
    fun `test add players - too many players`() {
        viewModel.run {
            onAddNewPlayer("1")
            onAddNewPlayer("2")
            onAddNewPlayer("3")
            onAddNewPlayer("4")
            onAddNewPlayer("5")
            onAddNewPlayer("6")
        }

        assertThrows(Error::class.java) {
            viewModel.onAddNewPlayer("7")
        }
    }

    @Test
    fun `test start game - less than minimum player amount`() {
        viewModel.run {
            onAddNewPlayer("1")
            onStartGame()
        }

        coVerify(exactly = 0) { repository.startGame(any()) }
    }
}