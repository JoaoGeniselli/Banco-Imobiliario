package com.dosei.games.toybank.home.presentation

import app.cash.turbine.test
import com.dosei.games.toybank.home.data.usecase.HasOngoingGame
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var hasOngoingGame: HasOngoingGame
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        hasOngoingGame = mockk()
        viewModel = HomeViewModel(hasOngoingGame)
    }

    @Test
    fun `enable continue button when ongoing game exists`() = runTest {
        coEvery { hasOngoingGame() } returns true
        viewModel.isContinueEnabled().test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `disable continue button when there is no ongoing game`() = runTest {
        coEvery { hasOngoingGame() } returns true
        viewModel.isContinueEnabled().test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }
}