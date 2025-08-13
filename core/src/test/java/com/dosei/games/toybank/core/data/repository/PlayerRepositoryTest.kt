package com.dosei.games.toybank.core.data.repository

import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.model.error.ErrorCode
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.storage.player.PlayerDao
import com.dosei.games.toybank.test.coAssertThrows
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PlayerRepositoryTest {

    private lateinit var dao: PlayerDao
    private lateinit var repository: PlayerRepository

    @Before
    fun setup() {
        dao = mockk(relaxed = true) {
            coEvery { fetchAllPlayers() } returns emptyFlow()
        }
        repository = PlayerRepository(dao)
    }

    @Test
    fun `clear data source and insert new players when override is called`() = runTest {
        val players = listOf(Player(), Player())
        repository.overridePlayerList(players)

        coVerify { dao.clearAll() }
        coVerify { dao.insertAll(players) }
    }

    @Test
    fun `append balance and save player when deposit is called`() = runTest {
        val player = Player(balanceInCents = 50)
        coEvery { dao.fetchPlayerById(any()) } returns player

        val resultPlayer = repository.deposit(1, 20)

        assertEquals(Player(balanceInCents = 70), resultPlayer)
        coVerify { dao.fetchPlayerById(1) }
        coVerify { dao.insert(Player(balanceInCents = 70)) }
    }

    @Test
    fun `remove balance and save player when withdraw is called`() = runTest {
        val player = Player(balanceInCents = 50)
        coEvery { dao.fetchPlayerById(any()) } returns player

        val resultPlayer = repository.withdraw(1, 30)

        assertEquals(Player(balanceInCents = 20), resultPlayer)
        coVerify { dao.fetchPlayerById(1) }
        coVerify { dao.insert(Player(balanceInCents = 20)) }
    }

    @Test
    fun `throw error when deposit player is unknown`() = runTest {
        coEvery { dao.fetchPlayerById(1) } returns null
        val error = coAssertThrows<BusinessException> {
            repository.withdraw(1, 30)
        }
        assertEquals(ErrorCode.INVALID_PLAYER, error.code)
    }

    @Test
    fun `throw error when withdraw player is unknown`() = runTest {
        coEvery { dao.fetchPlayerById(1) } returns null
        val error = coAssertThrows<BusinessException> {
            repository.withdraw(1, 30)
        }
        assertEquals(ErrorCode.INVALID_PLAYER, error.code)
    }

    @Test
    fun `delegate players flow`() = runTest {
        assertEquals(repository.players, dao.fetchAllPlayers())
    }

    @Test
    fun `delegate players count`() = runTest {
        coEvery { dao.fetchPlayersCount() } returns 5
        assertEquals(5, repository.countPlayers())
    }
}