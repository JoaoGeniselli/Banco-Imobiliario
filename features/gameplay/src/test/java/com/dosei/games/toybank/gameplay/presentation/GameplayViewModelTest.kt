package com.dosei.games.toybank.gameplay.presentation

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.storage.player.Player
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameplayViewModelTest {

    private lateinit var repository: PlayerRepository
    private lateinit var viewModel: GameplayViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = GameplayViewModel(repository)
    }

    @Test
    fun `check fetch players`() = runTest {
        val players = listOf(
            Player(1),
            Player(2)
        )
        every { repository.players } returns flowOf(players)
        viewModel.fetchPlayers().test {
            assertEquals(emptyList<Player>(), awaitItem()) // state initializer
            assertEquals(players, awaitItem())
        }
    }
}