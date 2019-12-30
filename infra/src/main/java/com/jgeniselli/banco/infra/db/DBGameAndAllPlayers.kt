package com.jgeniselli.banco.infra.db

import androidx.room.Embedded
import androidx.room.Relation

data class DBGameAndAllPlayers(

    @Embedded
    val game: DBGame,

    @Relation(parentColumn = "id", entityColumn = "game_id", entity = DBPlayer::class)
    val players: List<DBPlayer>
)