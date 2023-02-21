package com.jgeniselli.banco.core.usecase

import com.jgeniselli.banco.core.repository.GameStorage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HasOngoingGameTest {

    lateinit var storage: GameStorage
    lateinit var hasOngoingGame: HasOngoingGame

    @Before
    fun setup() {
        storage = mockk(relaxed = true)
        hasOngoingGame = HasOngoingGame(storage)
    }

    @Test
    fun `test delegation`() = runBlocking {
        coEvery { storage.isOngoingGameAvailable() } returns true
        assertTrue(hasOngoingGame())

        coEvery { storage.isOngoingGameAvailable() } returns false
        assertFalse(hasOngoingGame())

        coVerify(exactly = 2) { storage.isOngoingGameAvailable() }
    }

}