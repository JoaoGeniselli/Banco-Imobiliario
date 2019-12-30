package com.jgeniselli.banco.infra.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBCreditCardDao {

    @Query("select * from CreditCard")
    fun findAll() : List<DBCreditCard>

    @Query("select * from CreditCard where credit_card_id = :id")
    fun findById(id: Long) : DBCreditCard?

    @Insert
    fun insert(creditCard: DBCreditCard)
}