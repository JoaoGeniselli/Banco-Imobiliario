package com.jgeniselli.banco.game.history

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SwapHoriz
import com.jgeniselli.banco.R
import com.jgeniselli.banco.core.entities.OperationLog
import com.jgeniselli.banco.core.entities.Player
import com.jgeniselli.banco.ui.toolbox.toCurrency
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class OperationFormatterTest {

    @Test
    fun `test credit format`() {
        val context = mockk<Context>()
        val player = Player(1, "p1", 1uL, .0)
        val operation = OperationLog.Credit(player, 500.0)

        val result = OperationFormatter(context).format(operation)

        assertEquals(
            FormattedOperation(
                Icons.Default.Add,
                500.0.toCurrency(),
                player.name
            ),
            result
        )
    }

    @Test
    fun `test debit format`() {
        val context = mockk<Context>()
        val player = Player(1, "p1", 1uL, .0)
        val operation = OperationLog.Debit(player, 300.0)

        val result = OperationFormatter(context).format(operation)

        assertEquals(
            FormattedOperation(
                Icons.Default.Remove,
                300.0.toCurrency(),
                player.name
            ),
            result
        )
    }

    @Test
    fun `test transfer format`() {
        val context = mockk<Context> {
            every {
                getString(
                    eq(R.string.history_transfer),
                    any(),
                    any()
                )
            } returns "Mock string"
        }
        val source = Player(1, "p1", 1uL, .0)
        val recipient = Player(2, "p2", 2uL, .0)
        val operation = OperationLog.Transfer(source, recipient,950.0)

        val result = OperationFormatter(context).format(operation)

        assertEquals(
            FormattedOperation(
                Icons.Default.SwapHoriz,
                950.0.toCurrency(),
                "Mock string"
            ),
            result
        )

        verify {
            context.getString(
                R.string.history_transfer,
                source.name,
                recipient.name
            )
        }
    }
}