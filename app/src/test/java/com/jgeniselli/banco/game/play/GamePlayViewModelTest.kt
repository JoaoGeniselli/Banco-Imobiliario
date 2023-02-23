package com.jgeniselli.banco.game.play

import androidx.compose.ui.graphics.Color
import com.jgeniselli.banco.core.entity.Player
import com.jgeniselli.banco.core.repository.GameRepository
import com.jgeniselli.banco.ui.toolbox.toCurrency
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GamePlayViewModelTest {

    @Test
    fun `test players`() = runTest {
        val repository = mockk<GameRepository> {
            every { players } returns MutableStateFlow(
                listOf(
                    Player(1, "p1", Color.White.value, 510.0),
                    Player(2, "p2", Color.Red.value, 520.0),
                    Player(3, "p3", Color.Blue.value, 530.0),
                )
            )
        }
        val viewModel = GamePlayViewModel(repository)

        assertEquals(
            listOf(
                GameplayPlayer(1, Color.White, "p1", 510.0.toCurrency()),
                GameplayPlayer(2, Color.Red, "p2", 520.0.toCurrency()),
                GameplayPlayer(3, Color.Blue, "p3", 530.0.toCurrency()),
            ),
            viewModel.players.first()
        )
    }
}