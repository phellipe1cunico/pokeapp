package com.example.pokeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameAttemptDao {
    @Insert
    suspend fun insertAttempt(attempt: GameAttemptEntity)


    @Query("SELECT * FROM game_attempts WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAttemptsForUser(userId: Long): Flow<List<GameAttemptEntity>>


    @Query("DELETE FROM game_attempts WHERE userId = :userId")
    suspend fun clearHistory(userId: Long)
}