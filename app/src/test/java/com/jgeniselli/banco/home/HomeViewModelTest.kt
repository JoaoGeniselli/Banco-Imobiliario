package com.jgeniselli.banco.home

import com.jgeniselli.banco.core.usecase.HasOngoingGame
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @Test
    fun `test continue enabled`() = runTest {
        val hasOngoingGame = mockk<HasOngoingGame> {
            coEvery { this@mockk.invoke() } returns true
        }
        val vm = HomeViewModel(hasOngoingGame)

        assertTrue(vm.isContinueEnabled.first())

        coEvery { hasOngoingGame.invoke() } returns false

        assertFalse(vm.isContinueEnabled.last())
    }
}