package com.dosei.games.toybank.transaction.screen.beneficiary

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.repository.PlayerRepository
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.test.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransactionBeneficiaryViewModelTest {

    private lateinit var repository: PlayerRepository
    private lateinit var viewModel: TransactionBeneficiaryViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun before() {
        repository = mockk()
        viewModel = TransactionBeneficiaryViewModel(repository)
    }

    @Test
    fun `load beneficiary players without the source player`() = runTest {
        val playerA = Player(id = 1, "Player A")
        val playerB = Player(id = 2, "Player B")
        val playerC = Player(id = 3, "Player C")
        val allPlayers = listOf(playerA, playerB, playerC)

        every { repository.players } returns flowOf(allPlayers)

        viewModel.loadPlayers(2).test {
            assertEquals(
                listOf(playerA, playerC),
                awaitItem()
            )
            awaitComplete()
        }

    }

}