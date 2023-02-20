package com.jgeniselli.banco.infra.database

import androidx.room.Embedded
import androidx.room.Relation

data class HistoryWithPlayers(
    @Embedded val history: HistoryLogEntity,

    @Relation(
        parentColumn = "source_id",
        entityColumn = "id"
    )
    val source: PlayerEntity,

    @Relation(
        parentColumn = "recipient_id",
        entityColumn = "id"
    )
    val recipient: PlayerEntity?
)