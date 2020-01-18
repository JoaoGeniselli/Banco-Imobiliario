package com.jgeniselli.banco.infra.db

import androidx.room.*

@Dao
interface DBGameDao {

    @Query("select * from Player")
    fun findAll() : List<DBPlayer>

    @Query("select * from Player where id = :id")
    fun findById(id: Long) : DBPlayer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(players: List<DBPlayer>)

    @Delete
    fun delete(player: DBPlayer)

    @Query("update Player set cash = :cash where id = :playerId")
    fun updateCash(playerId: Long, cash: Double)

    @Query("update Player set cash = :cash")
    fun updateCashToAllPlayers(cash: Double)

    @Query("delete from Player")
    fun deleteAllPlayers()

    @Query("select P.color, T.cash from `Transaction` T inner join Player P where T.player_id = P.id order by T.id desc")
    fun findTransactionsWithPlayerColor() : List<ColorAndCashTuple>

    @Insert
    fun insertTransaction(transaction: DBTransaction)

    @Query("delete from `Transaction`")
    fun deleteAllTransactions()

}