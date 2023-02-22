package com.jgeniselli.banco.operations.transfer

import com.jgeniselli.banco.core.repository.GameRepository
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TransferViewModelTest {

    private val playerId: Int = 1
    private lateinit var gameRepository: GameRepository
    private lateinit var viewModel: TransferViewModel

    @Before
    fun before() {
        gameRepository = mockk(relaxed = true)
        viewModel = TransferViewModel(playerId, gameRepository)
    }

//    @Test
//    fun `test initial state`() = runBlocking {
//
//    }
}