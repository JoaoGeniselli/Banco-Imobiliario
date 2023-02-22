package com.jgeniselli.banco.game.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.jgeniselli.banco.core.entities.OperationLog
import com.jgeniselli.banco.core.entities.Player
import com.jgeniselli.banco.core.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    @Test
    fun `test history format`() = runTest {
        val player = Player(1, "1", 1uL, 0.0)
        val formatter = mockk<OperationFormatter> {
            val slot = slot<OperationLog>()
            every { format(capture(slot)) } answers {
                with(slot.captured as OperationLog.Credit) {
                    FormattedOperation(
                        Icons.Default.Add,
                        value.toString(),
                        player.name
                    )
                }
            }
        }
        val repository = mockk<GameRepository> {
            every { history } returns MutableStateFlow(
                listOf(
                    OperationLog.Credit(player, 100.0),
                    OperationLog.Credit(player, 200.0),
                    OperationLog.Credit(player, 300.0),
                )
            )
        }

        val viewModel = HistoryViewModel(repository, formatter)

        assertEquals(
            listOf(
                FormattedOperation(
                    Icons.Default.Add,
                    300.0.toString(),
                    player.name
                ),
                FormattedOperation(
                    Icons.Default.Add,
                    200.0.toString(),
                    player.name
                ),
                FormattedOperation(
                    Icons.Default.Add,
                    100.0.toString(),
                    player.name
                )
            ),
            viewModel.history.first()
        )


    }
}