package com.jgeniselli.banco.infra.database

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EntityExtensionsKtTest {

    @Test
    fun `test player entity to domain player`() {
        val entity = PlayerEntity(10, "john", 1L, 444.0)
        assertEquals(
            Player(10, "john", 1uL, 444.0),
            entity.toDomainPlayer()
        )
    }

    @Test
    fun `test operation log to entity`() {
        val source = Player(10, "john", 1uL, 444.0)
        val recipient = Player(20, "william", 2uL, 888.0)

        val credit = OperationLog.Credit(source, 85.0)
        assertEquals(
            HistoryLogEntity(0, LOG_TYPE_CREDIT, 10, null, 85.0),
            credit.toEntity()
        )

        val debit = OperationLog.Debit(source, 40.0)
        assertEquals(
            HistoryLogEntity(0, LOG_TYPE_DEBIT, 10, null, 40.0),
            debit.toEntity()
        )

        val transfer = OperationLog.Transfer(source, recipient, 25.0)
        assertEquals(
            HistoryLogEntity(0, LOG_TYPE_TRANSFER, 10, 20, 25.0),
            transfer.toEntity()
        )
    }

    @Test
    fun `test history with players to domain`() {
        val source = PlayerEntity(10, "john", 1L, 444.0)
        val recipient = PlayerEntity(20, "william", 2L, 888.0)

        val credit = HistoryLogEntity(0, LOG_TYPE_CREDIT, 10, null, 85.0)
        val debit = HistoryLogEntity(0, LOG_TYPE_DEBIT, 10, null, 40.0)
        val transfer = HistoryLogEntity(0, LOG_TYPE_TRANSFER, 10, 20, 25.0)
        val invalid = HistoryLogEntity(0, "J", 10, null, 15.0)

        assertEquals(
            OperationLog.Credit(source.toDomainPlayer(), 85.0),
            HistoryWithPlayers(credit, source, null).toDomainOperation()
        )

        assertEquals(
            OperationLog.Debit(source.toDomainPlayer(), 40.0),
            HistoryWithPlayers(debit, source, null).toDomainOperation()
        )

        assertEquals(
            OperationLog.Transfer(source.toDomainPlayer(), recipient.toDomainPlayer(), 25.0),
            HistoryWithPlayers(transfer, source, recipient).toDomainOperation()
        )

        assertThrows(NullPointerException::class.java) {
            HistoryWithPlayers(transfer, source, null).toDomainOperation()
        }

        assertThrows(Error::class.java) {
            HistoryWithPlayers(invalid, source, null).toDomainOperation()
        }
    }
}