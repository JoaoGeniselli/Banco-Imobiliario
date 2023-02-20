package com.jgeniselli.banco.infra.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

const val HISTORY_RESULT_LIMIT = 30

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history LIMIT $HISTORY_RESULT_LIMIT")
    fun getAll(): Flow<List<HistoryLogEntity>>

    @Insert
    suspend fun insertAll(vararg logs: HistoryLogEntity)

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}