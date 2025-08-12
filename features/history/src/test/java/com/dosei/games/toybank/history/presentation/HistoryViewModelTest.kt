package com.dosei.games.toybank.history.presentation

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.history.data.model.HistoryEntry
import com.dosei.games.toybank.history.data.usecase.LoadHistory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class HistoryViewModelTest {

    private lateinit var loadHistory: LoadHistory
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setup() {
        loadHistory = mockk()
        viewModel = HistoryViewModel(loadHistory)
    }

    @Test
    fun `check history flow`() = runTest {
        val entries = listOf(
            HistoryEntry.Deposit(25000, Date(), Player(name = "Oliver")),
            HistoryEntry.Withdraw(30000, Date(), Player(name = "Jeniffer")),
        )
        every { loadHistory.invoke() } returns flowOf(entries)
        viewModel.fetchHistory().test {
            assertEquals(emptyList<HistoryEntry>(), awaitItem()) // initial state value
            assertEquals(entries, awaitItem())
        }
    }

}