package com.jgeniselli.banco.infra.database

import com.jgeniselli.banco.core.entity.OperationLog
import com.jgeniselli.banco.core.entity.Player

fun PlayerEntity.toDomainPlayer() = Player(
    id = id,
    name = name,
    color = color.toULong(),
    balance = balance
)

fun OperationLog.toEntity(): HistoryLogEntity =
    when (this) {
        is OperationLog.Credit -> HistoryLogEntity(
            0,
            LOG_TYPE_CREDIT,
            player.id,
            null,
            value
        )
        is OperationLog.Debit -> HistoryLogEntity(
            0,
            LOG_TYPE_DEBIT,
            player.id,
            null,
            value
        )
        is OperationLog.Transfer -> HistoryLogEntity(
            0,
            LOG_TYPE_TRANSFER,
            source.id,
            recipient.id,
            value
        )
    }

fun HistoryWithPlayers.toDomainOperation() =
    when (this.history.type) {
        LOG_TYPE_CREDIT -> OperationLog.Credit(source.toDomainPlayer(), history.value)
        LOG_TYPE_DEBIT -> OperationLog.Debit(source.toDomainPlayer(), history.value)
        LOG_TYPE_TRANSFER -> OperationLog.Transfer(
            source.toDomainPlayer(),
            recipient!!.toDomainPlayer(),
            history.value
        )
        else -> throw Error("Unknown type")
    }