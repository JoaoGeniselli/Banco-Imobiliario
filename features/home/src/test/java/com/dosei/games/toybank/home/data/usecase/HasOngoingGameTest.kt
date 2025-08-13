package com.dosei.games.toybank.home.data.usecase

import com.dosei.games.toybank.core.data.repository.PlayerRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HasOngoingGameTest {

    private lateinit var repository: PlayerRepository
    private lateinit var hasOngoingGame: HasOngoingGame

    @Before
    fun setup() {
        repository = mockk()
        hasOngoingGame = HasOngoingGame(repository)
    }

    @Test
    fun `return true when there are more than zero saved players`() = runTest {
        coEvery { repository.countPlayers() } returns 2
        assertTrue(hasOngoingGame())
        coVerify { repository.countPlayers() }
    }

    @Test
    fun `return false when there are none saved players`() = runTest {
        coEvery { repository.countPlayers() } returns 0
        assertFalse(hasOngoingGame())
        coVerify { repository.countPlayers() }
    }
}