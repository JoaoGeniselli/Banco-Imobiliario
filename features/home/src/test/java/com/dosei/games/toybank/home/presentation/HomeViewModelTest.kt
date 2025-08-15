package com.dosei.games.toybank.home.presentation

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.model.GameState
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.usecase.CheckGameState
import com.dosei.games.toybank.test.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HomeViewModelTest {

    private lateinit var checkGameState: CheckGameState
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        checkGameState = mockk()
        viewModel = HomeViewModel(checkGameState)
    }

    @Test
    fun `enable continue button when ongoing game exists`() = runTest {
        coEvery { checkGameState() } returns flowOf(GameState.Ongoing(emptyList()))
        viewModel.isContinueEnabled().test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `disable continue button when there is no ongoing game`() = runTest {
        coEvery { checkGameState() } returns flowOf(GameState.NotStarted)
        viewModel.isContinueEnabled().test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `disable continue button when current game is over`() = runTest {
        coEvery { checkGameState() } returns flowOf(GameState.GameOver(Player()))
        viewModel.isContinueEnabled().test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }
}