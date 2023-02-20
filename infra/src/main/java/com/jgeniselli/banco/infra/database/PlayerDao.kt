package com.jgeniselli.banco.infra.database

import androidx.room.*
import com.jgeniselli.banco.core.repository.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player")
    fun getAll(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE id = (:playerId)")
    suspend fun getById(playerId: Int): PlayerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<PlayerEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(player: PlayerEntity)

    @Query("DELETE FROM player")
    suspend fun deleteAll()
}