package com.dosei.games.toybank.data.local.storage.player

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players")
    fun fetchAllPlayers(): Flow<List<Player>>

    @Query("SELECT COUNT(*) FROM players")
    suspend fun fetchPlayersCount(): Int

    @Query("UPDATE players SET balance = :newBalanceInCents WHERE id = :playerId")
    suspend fun updatePlayerBalance(playerId: Int, newBalanceInCents: Int)

    @Query("SELECT balance FROM players WHERE id = :playerId")
    suspend fun fetchPlayerBalance(playerId: Int): Int

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<Player>)

    @Delete
    suspend fun deleteAll(vararg players: Player)

    @Query("DELETE FROM players")
    suspend fun clearAll()
}