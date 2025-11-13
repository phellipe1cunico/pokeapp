package com.example.pokeapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ForumPostDao {

    // Retorna todos os posts, mais recentes primeiro
    @Query("SELECT * FROM forum_posts ORDER BY timestamp DESC")
    fun getAllPosts(): Flow<List<ForumPostEntity>>

    // Usado para criar novos posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: ForumPostEntity)

    // Usado para salvar edições
    @Update
    suspend fun updatePost(post: ForumPostEntity)

    // Usado para deletar
    @Delete
    suspend fun deletePost(post: ForumPostEntity)
}