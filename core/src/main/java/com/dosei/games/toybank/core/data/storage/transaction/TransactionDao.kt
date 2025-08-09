package com.dosei.games.toybank.core.data.storage.transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT :limit")
    fun fetchLastTransactions(limit: Int = DEFAULT_LIMIT): Flow<List<TransactionEntity>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg transactions: TransactionEntity)

    @Delete
    suspend fun deleteAll(vararg transactions: TransactionEntity)

    @Query("DELETE FROM transactions")
    suspend fun clearAll()

    companion object {
        const val DEFAULT_LIMIT = 10
    }
}