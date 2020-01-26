package com.jgeniselli.banco.core

import com.jgeniselli.banco.core.boundary.PlayerStorage
import com.jgeniselli.banco.core.dto.StoredPlayerDto
import com.jgeniselli.banco.core.dto.StoredTransactionDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameAPITest {

    private lateinit var gameSetup: GameSetup
    private lateinit var storage: PlayerStorage
    private lateinit var api: GameAPI

    @Before
    fun before() {
        storage = mockk(relaxed = true)
        gameSetup = mockk()

        every { gameSetup.initialCash } returns 25000.00
        every { gameSetup.availableColorsInHex } returns listOf("#FFFFFF", "#000000")
        api = GameAPI(gameSetup, storage)
    }

    @Test
    fun `when players requested then delegate to storage`() {
        val expectedPlayers = listOf(
            StoredPlayerDto(1, "#FFFFFF", 2000.0),
            StoredPlayerDto(2, "#000000", 5000.0)
        )
        every {
            storage.findAllPlayers(any())
        } answers {
            firstArg<Callback<List<StoredPlayerDto>>>().invoke(expectedPlayers)
        }

        lateinit var fetchedPlayers: List<StoredPlayerDto>

        api.getPlayers { fetchedPlayers = it }

        assertEquals(2, fetchedPlayers.size)
        assertEquals(expectedPlayers[0], fetchedPlayers[0])
        assertEquals(expectedPlayers[1], fetchedPlayers[1])

        verify { storage.findAllPlayers(any()) }
    }

    @Test
    fun `when debit requested with valid value then delegate to storage`() {
        every {
            storage.addTransaction(any(), any(), any())
        } answers {
            thirdArg<ResultlessCallback>().invoke()
        }
        val callback = mockk<ResultlessCallback>(relaxed = true)

        api.debit(1, 1500.0, callback)

        verify { storage.addTransaction(1, -1500.0, any()) }
        verify { callback.invoke() }
    }

    @Test(expected = IllegalTransactionValueException::class)
    fun `when debit requested with negative value then throw error`() {
        api.debit(1, -500.0) {}
        verify(exactly = 0) { storage.addTransaction(any(), any(), any()) }
    }

    @Test
    fun `when credit requested with valid value then delegate to storage`() {
        every {
            storage.addTransaction(any(), any(), any())
        } answers {
            thirdArg<ResultlessCallback>().invoke()
        }
        val callback = mockk<ResultlessCallback>(relaxed = true)

        api.credit(1, 1500.0, callback)

        verify { storage.addTransaction(1, 1500.0, any()) }
        verify { callback.invoke() }
    }

    @Test(expected = IllegalTransactionValueException::class)
    fun `when credit requested with negative value then throw error`() {
        api.credit(1, -500.0) {}
        verify(exactly = 0) { storage.addTransaction(any(), any(), any()) }
    }

    @Test
    fun `when transfer requested then debit from source and credit to destiny player`() {
        every {
            storage.addTransaction(any(), any(), any())
        } answers {
            thirdArg<ResultlessCallback>().invoke()
        }
        val callback = mockk<ResultlessCallback>(relaxed = true)

        api.transfer(1, 2, 1500.0, callback)

        verify { storage.addTransaction(1, -1500.0, any()) }
        verify { storage.addTransaction(2, +1500.0, any()) }
        verify { callback.invoke() }
    }

    @Test
    fun `when 'start game if needed' with existing game then avoid creating game`() {
        every {
            storage.isGameGoingOn(any())
        } answers {
            firstArg<Callback<Boolean>>().invoke(true)
        }

        val callback = mockk<ResultlessCallback>(relaxed = true)
        api.startGameIfNeeded(callback)

        verify { storage.isGameGoingOn(any()) }
        verify { callback.invoke() }
        verify(exactly = 0) { storage.clearPlayersAndTransactions(any()) }
        verify(exactly = 0) { storage.createPlayersForColors(any(), any(), any()) }
    }

    @Test
    fun `when 'start game if needed' with no game then create new game`() {
        every {
            storage.isGameGoingOn(any())
        } answers {
            firstArg<Callback<Boolean>>().invoke(false)
        }

        every {
            storage.clearPlayersAndTransactions(any())
        } answers {
            firstArg<ResultlessCallback>().invoke()
        }

        every {
            storage.createPlayersForColors(any(), any(), any())
        } answers {
            thirdArg<ResultlessCallback>().invoke()
        }

        val callback = mockk<ResultlessCallback>(relaxed = true)
        api.startGameIfNeeded(callback)

        verify { storage.isGameGoingOn(any()) }
        verify { storage.clearPlayersAndTransactions(any()) }
        verify { storage.createPlayersForColors(listOf("#FFFFFF", "#000000"), 25000.0, any()) }
    }

    @Test
    fun `when reset game requested then create new game`() {
        every {
            storage.clearPlayersAndTransactions(any())
        } answers {
            firstArg<ResultlessCallback>().invoke()
        }

        every {
            storage.createPlayersForColors(any(), any(), any())
        } answers {
            thirdArg<ResultlessCallback>().invoke()
        }

        val callback = mockk<ResultlessCallback>(relaxed = true)
        api.resetGame(callback)

        verify { storage.clearPlayersAndTransactions(any()) }
        verify { storage.createPlayersForColors(listOf("#FFFFFF", "#000000"), 25000.0, any()) }
        verify { callback.invoke() }
    }

    @Test
    fun `when history requested then delegate to storage`() {
        val expectedHistory = listOf(
            StoredTransactionDto(150.0, "#FFFFFF"),
            StoredTransactionDto(-20.0, "#000000")
        )
        every {
            storage.findTransactionHistory(any())
        } answers {
            firstArg<Callback<List<StoredTransactionDto>>>().invoke(expectedHistory)
        }

        lateinit var fetchedHistory: List<StoredTransactionDto>

        api.getTransactionHistory {
            fetchedHistory = it
        }

        verify { storage.findTransactionHistory(any()) }
        assertEquals(2, fetchedHistory.size)
        assertEquals(expectedHistory[0], fetchedHistory[0])
        assertEquals(expectedHistory[1], fetchedHistory[1])
    }

}