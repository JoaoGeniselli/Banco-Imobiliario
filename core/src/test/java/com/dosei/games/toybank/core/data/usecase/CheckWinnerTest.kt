package com.dosei.games.toybank.core.data.usecase

import com.dosei.games.toybank.core.data.storage.player.Player
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CheckWinnerTest {

    private lateinit var checkWinner: CheckWinner

    @Before
    fun setup() {
        checkWinner = CheckWinner()
    }

    @Test
    fun `should return null when multiple players has positive balance`() {
        val players = listOf(
            Player(id = 1, balanceInCents = 500),
            Player(id = 2, balanceInCents = -300),
            Player(id = 3, balanceInCents = 250),
        )
        assertNull(checkWinner(players))
    }

    @Test
    fun `should return winner when there is only one player with positive balance`() {
        val winner = Player(id = 3, balanceInCents = 250)
        val players = listOf(
            Player(id = 1, balanceInCents = -500),
            Player(id = 2, balanceInCents = -300),
            winner,
        )
        assertEquals(winner, checkWinner(players))
    }
}