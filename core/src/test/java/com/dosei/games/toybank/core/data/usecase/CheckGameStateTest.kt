package com.dosei.games.toybank.core.data.usecase

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.model.GameState
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.storage.player.Player
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CheckGameStateTest {

    @MockK
    lateinit var repository: PlayerRepository

    @MockK
    lateinit var checkWinner: CheckWinner

    private lateinit var checkGameState: CheckGameState

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        checkGameState = CheckGameState(repository, checkWinner)
    }

    @Test
    fun `should return NotStarted when there are no saved players`() = runTest {
        every { repository.players } returns flowOf(emptyList())

        checkGameState().test {
            assertEquals(GameState.NotStarted, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should return GameOver when there is an available winner`() = runTest {
        val players = listOf(
            Player(id = 1),
            Player(id = 2),
            Player(id = 3)
        )
        every { repository.players } returns flowOf(players)
        every { checkWinner(any()) } returns players.first()

        checkGameState().test {
            assertEquals(
                GameState.GameOver(players.first()),
                awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `should return Ongoing when there are saved players with no winner`() = runTest {
        val players = listOf(
            Player(id = 1),
            Player(id = 2),
            Player(id = 3)
        )
        every { repository.players } returns flowOf(players)
        every { checkWinner(any()) } returns null

        checkGameState().test {
            assertEquals(
                GameState.Ongoing(players),
                awaitItem()
            )
            awaitComplete()
        }
    }
}