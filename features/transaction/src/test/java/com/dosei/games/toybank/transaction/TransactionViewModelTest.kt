package com.dosei.games.toybank.transaction

import app.cash.turbine.test
import com.dosei.games.toybank.core.data.model.Close
import com.dosei.games.toybank.core.data.model.NavigateTo
import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.transaction.data.usecase.PerformTransaction
import com.dosei.games.toybank.transaction.navigation.TransactionRoutes
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModelTest {

    private lateinit var performTransaction: PerformTransaction
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setup() {
        performTransaction = mockk(relaxed = true)
        viewModel = TransactionViewModel(performTransaction)
    }

    @Test
    fun `update player id on start`() = runTest {
        viewModel.start(5)

        viewModel.state.test {
            assertEquals(5, awaitItem().playerId)
        }
    }

    @Test
    fun `update type and navigate to beneficiary when transfer is selected`() = runTest {
        viewModel.onSelectType(TransactionType.TRANSFER)

        viewModel.state.test {
            assertEquals(TransactionType.TRANSFER, awaitItem().type)
        }
        advanceUntilIdle()
        viewModel.events.test {
            assertEquals(
                NavigateTo(TransactionRoutes.BeneficiarySelection),
                awaitItem()
            )
        }
    }

    @Test
    fun `update type and navigate to amount when deposit is selected`() = runTest {
        viewModel.onSelectType(TransactionType.DEPOSIT)
        viewModel.state.test {
            assertEquals(TransactionType.DEPOSIT, awaitItem().type)
        }
        advanceUntilIdle()
        viewModel.events.test {
            assertEquals(
                NavigateTo(TransactionRoutes.AmountInput),
                awaitItem()
            )
        }
    }

    @Test
    fun `update type and navigate to amount when withdraw is selected`() = runTest {
        viewModel.onSelectType(TransactionType.WITHDRAW)

        viewModel.state.test {
            assertEquals(TransactionType.WITHDRAW, awaitItem().type)
        }
        advanceUntilIdle()
        viewModel.events.test {
            assertEquals(
                NavigateTo(TransactionRoutes.AmountInput),
                awaitItem()
            )
        }
    }

    @Test
    fun `update state and navigate to amount when beneficiary is selected`() = runTest {
        viewModel.onBeneficiarySelected(2)

        viewModel.state.test {
            assertEquals(2, awaitItem().destinationPlayerId)
        }
        advanceUntilIdle()
        viewModel.events.test {
            assertEquals(
                NavigateTo(TransactionRoutes.AmountInput),
                awaitItem()
            )
        }
    }

    @Test
    fun `perform transaction and close when confirm amount`() = runTest {
        viewModel.run {
            start(1)
            onSelectType(TransactionType.DEPOSIT)
            events.test { assertEquals(NavigateTo(TransactionRoutes.AmountInput), awaitItem()) }
            onConfirmAmount(500)
            events.test { assertEquals(Close, awaitItem()) }
        }

        viewModel.state.test {
            assertEquals(500, awaitItem().amountInCents)
        }

        coVerify {
            performTransaction(
                TransactionState(
                    type = TransactionType.DEPOSIT,
                    amountInCents = 500,
                    playerId = 1,
                    destinationPlayerId = null
                )
            )
        }
    }

}