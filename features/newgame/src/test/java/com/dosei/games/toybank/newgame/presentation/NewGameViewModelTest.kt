package com.dosei.games.toybank.newgame.presentation

import androidx.compose.ui.graphics.toArgb
import app.cash.turbine.test
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.navigation.AppRoutes
import com.dosei.games.toybank.newgame.data.model.LeadPlayer
import com.dosei.games.toybank.newgame.data.usecase.StartNewGame
import com.dosei.games.toybank.ui.theme.Amber
import com.dosei.games.toybank.ui.theme.Blue500
import com.dosei.games.toybank.ui.theme.DeepOrange
import com.dosei.games.toybank.ui.theme.Green
import com.dosei.games.toybank.ui.theme.Grey
import com.dosei.games.toybank.ui.theme.Purple
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewGameViewModelTest {

    private lateinit var startNewGame: StartNewGame
    private lateinit var viewModel: NewGameViewModel

    @Before
    fun setup() {
        startNewGame = mockk(relaxed = true)
        viewModel = NewGameViewModel(startNewGame)
    }

    @Test
    fun `update initial balance when requested`() = runTest {
        viewModel.onUpdateInitialBalance(200_00)
        assertEquals(200_00, viewModel.state.value.initialBalanceInCents)
    }

    @Test
    fun `update player list and remove available color when player is added`() = runTest {
        assertEquals(emptyList<LeadPlayer>(), viewModel.state.value.players)
        viewModel.run {
            createPlayer("John", Purple)
            createPlayer("Mary", DeepOrange)
        }

        assertEquals(
            listOf(
                LeadPlayer("John", Purple.toArgb()),
                LeadPlayer("Mary", DeepOrange.toArgb())
            ),
            viewModel.state.value.players
        )

        assertEquals(
            listOf(Green, Amber, Grey, Blue500),
            viewModel.state.value.availableColors
        )
    }

    @Test
    fun `update player list and make color available when player is removed`() = runTest {
        viewModel.run {
            createPlayer("John", Purple)
            createPlayer("Mary", DeepOrange)
            removePlayer(LeadPlayer("John", Purple.toArgb()))
        }
        assertEquals(
            listOf(LeadPlayer("Mary", DeepOrange.toArgb())),
            viewModel.state.value.players
        )

        assertEquals(
            listOf(Green, Amber, Grey, Blue500, Purple),
            viewModel.state.value.availableColors
        )
    }

    @Test
    fun `start game and notify navigation when new game is clicked`() = runTest {
        viewModel.run {
            onUpdateInitialBalance(300_00)
            createPlayer("John", Purple)
            createPlayer("Mary", DeepOrange)
            onNewGameClick()
        }

        coVerify {
            startNewGame(
                leadPlayers = listOf(
                    LeadPlayer("John", Purple.toArgb()),
                    LeadPlayer("Mary", DeepOrange.toArgb())
                ),
                initialBalance = 300_00
            )
        }

        viewModel.events.test {
            assertEquals(NavigateTo(AppRoutes.Game.Play), awaitItem())
        }
    }

}