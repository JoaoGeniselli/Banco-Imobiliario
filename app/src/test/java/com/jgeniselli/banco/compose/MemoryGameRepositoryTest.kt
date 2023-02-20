package com.jgeniselli.banco.compose

import com.jgeniselli.banco.core.repository.MemoryGameRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MemoryGameRepositoryTest {

    lateinit var repository: MemoryGameRepository

    @Before
    fun setup() {
        repository = MemoryGameRepository()
    }

    @Test
    fun `test credit`() = runBlocking {
        val id = 1
        assertEquals(1000.0, repository.players.value.first { it.id == id }.balance, .001)

        repository.credit(id, 500.0)
        assertEquals(1500.0, repository.players.value.first { it.id == id }.balance, .001)
    }

    @Test
    fun `test debit`() = runBlocking {
        val id = 1
        assertEquals(1000.0, repository.players.value.first { it.id == id }.balance, .001)

        repository.debit(id, 500.0)
        assertEquals(500.0, repository.players.value.first { it.id == id }.balance, .001)
    }

    @Test
    fun `test transfer`() = runBlocking {
        val source = 1
        val recipient = 2
        assertEquals(1000.0, repository.players.value.first { it.id == source }.balance, .001)
        assertEquals(1000.0, repository.players.value.first { it.id == recipient }.balance, .001)

        repository.transfer(source, recipient, 500.0)
        assertEquals(500.0, repository.players.value.first { it.id == source }.balance, .001)
        assertEquals(1500.0, repository.players.value.first { it.id == recipient }.balance, .001)
    }

}