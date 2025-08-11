package com.dosei.games.toybank.newgame.data.usecase

import androidx.compose.ui.graphics.toArgb
import com.dosei.games.toybank.core.data.model.error.BusinessException
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.repository.TransactionRepository
import com.dosei.games.toybank.newgame.data.mapper.toNewEntity
import com.dosei.games.toybank.newgame.data.model.LeadPlayer
import com.dosei.games.toybank.test.coAssertThrows
import com.dosei.games.toybank.ui.theme.DeepOrange
import com.dosei.games.toybank.ui.theme.Purple
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class StartNewGameTest {

    @RelaxedMockK
    private lateinit var playerRepository: PlayerRepository

    @RelaxedMockK
    private lateinit var transactionRepository: TransactionRepository

    private lateinit var startNewGame: StartNewGame

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        startNewGame = StartNewGame(playerRepository, transactionRepository)
    }

    @Test
    fun `throw error when balance is invalid`() = runTest {
        val players = listOf(
            LeadPlayer("John", Purple.toArgb()),
            LeadPlayer("William", DeepOrange.toArgb()),
        )
        coAssertThrows<BusinessException> {
            startNewGame(leadPlayers = players, initialBalance = -1)
        }
        coAssertThrows<BusinessException> {
            startNewGame(leadPlayers = players, initialBalance = 0)
        }
    }

    @Test
    fun `throw error when player amount is invalid`() = runTest {
        coAssertThrows<BusinessException> {
            startNewGame(
                leadPlayers = emptyList(),
                initialBalance = 200
            )
        }
        coAssertThrows<BusinessException> {
            startNewGame(
                leadPlayers = listOf(
                    LeadPlayer("John", Purple.toArgb()),
                ),
                initialBalance = 200
            )
        }
        coAssertThrows<BusinessException> {
            startNewGame(
                leadPlayers = MutableList(PLAYERS_RANGE.last.inc()) { v ->
                    LeadPlayer("$v", v)
                },
                initialBalance = 200
            )
        }
    }

    @Test
    fun `setup new game when received data is valid`() = runTest {
        val leads = listOf(
            LeadPlayer("John", Purple.toArgb()),
            LeadPlayer("William", DeepOrange.toArgb()),
        )

        startNewGame(
            leadPlayers = leads,
            initialBalance = 3000_00
        )

        coVerify {
            playerRepository.overridePlayerList(
                newPlayers = leads.map { it.toNewEntity(3000_00) }
            )
        }
        coVerify { transactionRepository.clearHistory() }
    }

}