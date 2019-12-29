package com.jgeniselli.banco.game.common.domain.db

import androidx.room.*

@Dao
interface DBPlayerDao {

    @Query("select * from Player")
    fun findAll() : List<DBPlayer>

    @Query("select * from Player where id = :id")
    fun findById(id: Long) : DBPlayer?

    @Insert
    fun insert(player: DBPlayer)

    @Delete
    fun delete(player: DBPlayer)

    @Update
    fun update(player: DBPlayer)

    @Query("select * from Player")
    fun findAllWithCreditCard()
}