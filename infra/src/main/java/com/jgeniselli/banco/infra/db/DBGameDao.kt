package com.jgeniselli.banco.infra.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBGameDao {

    @Query("select * from Game")
    fun findAll(): List<DBGame>

    @Query("select * from Game")
    fun findAllWithPlayers() : List<DBGameAndAllPlayers>

    @Query("select * from Game where id = :id")
    fun findById(id: Long): DBGame?

    @Insert
    fun insert(game: DBGame)

    @Delete
    fun delete(game: DBGame)
}