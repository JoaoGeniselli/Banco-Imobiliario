package com.jgeniselli.banco.game.common.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBCreditCardDao {

    @Query("select * from CreditCard")
    fun findAll() : List<DBCreditCard>

    @Query("select * from CreditCard where id = :id")
    fun findById(id: Long) : DBCreditCard?

    @Insert
    fun insert(creditCard: DBCreditCard)
}