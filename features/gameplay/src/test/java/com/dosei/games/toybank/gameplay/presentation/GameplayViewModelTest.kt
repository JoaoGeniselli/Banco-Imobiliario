package com.dosei.games.toybank.gameplay.presentation

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.model.GameState
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.usecase.CheckGameState
import com.dosei.games.toybank.gameplay.presentation.play.GameplayViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GameplayViewModelTest {

    private lateinit var checkGameState: CheckGameState
    private lateinit var viewModel: GameplayViewModel

    @Before
    fun setup() {
        checkGameState = mockk()
        viewModel = GameplayViewModel(checkGameState)
    }

    @Test
    fun `should provide players when game is ongoing`() = runTest {
        val players = listOf(
            Player(1),
            Player(2)
        )
        every { checkGameState() } returns flowOf(GameState.Ongoing(players))
        viewModel.fetchPlayers().test {
            assertEquals(players, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should provide winner when game is over`() = runTest {
        val winner = Player(1)
        every { checkGameState() } returns flowOf(GameState.GameOver(winner))
        viewModel.observeWinner().test {
            assertEquals(winner, awaitItem())
            awaitComplete()
        }
    }
}