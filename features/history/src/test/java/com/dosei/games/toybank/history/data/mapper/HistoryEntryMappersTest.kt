package com.dosei.games.toybank.history.data.mapper

import com.dosei.games.toybank.core.data.model.TransactionType
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.storage.transaction.TransactionEntity
import com.dosei.games.toybank.history.data.model.HistoryEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Date

class HistoryEntryMappersTest {

    private val playerA = Player(id = 1, name = "A")
    private val playerB = Player(id = 2, name = "B")
    private val players = listOf(playerA, playerB)
    private val timestamp = Date().time

    @Test
    fun `check mapper - deposit`() {
        assertEquals(
            HistoryEntry.Deposit(
                amount = 350,
                date = Date(timestamp),
                sourcePlayer = playerB
            ),
            TransactionEntity(
                id = 0,
                type = TransactionType.DEPOSIT.id,
                sourcePlayerId = 2,
                amountInCents = 350,
                timestamp = timestamp
            ).toHistoryEntry(players)
        )
    }

    @Test
    fun `check mapper - withdraw`() {
        assertEquals(
            HistoryEntry.Withdraw(
                amount = 200,
                date = Date(timestamp),
                sourcePlayer = playerA
            ),
            TransactionEntity(
                id = 0,
                type = TransactionType.WITHDRAW.id,
                sourcePlayerId = 1,
                amountInCents = 200,
                timestamp = timestamp
            ).toHistoryEntry(players)
        )
    }

    @Test
    fun `check mapper - transfer`() {
        assertEquals(
            HistoryEntry.Transfer(
                amount = 200,
                date = Date(timestamp),
                sourcePlayer = playerA,
                destinationPlayer = playerB
            ),
            TransactionEntity(
                id = 0,
                type = TransactionType.TRANSFER.id,
                sourcePlayerId = 1,
                destinationPlayerId = 2,
                amountInCents = 200,
                timestamp = timestamp
            ).toHistoryEntry(players)
        )
    }

    @Test
    fun `check mapper - unknown`() {
        assertNull(
            TransactionEntity(
                id = 0,
                type = 'X',
                sourcePlayerId = 1,
                destinationPlayerId = 2,
                amountInCents = 200,
                timestamp = timestamp
            ).toHistoryEntry(players)
        )
    }
}