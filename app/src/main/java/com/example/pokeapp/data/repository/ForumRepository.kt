package com.example.pokeapp.data.repository

import com.example.pokeapp.data.local.ForumPostDao
import com.example.pokeapp.data.local.ForumPostEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositório para gerenciar as operações do Fórum (CRUD).
 */
class ForumRepository(private val forumPostDao: ForumPostDao) {

    fun getAllPosts(): Flow<List<ForumPostEntity>> {
        return forumPostDao.getAllPosts()
    }

    suspend fun addPost(post: ForumPostEntity) {
        forumPostDao.insertPost(post)
    }

    suspend fun updatePost(post: ForumPostEntity) {
        forumPostDao.updatePost(post)
    }

    suspend fun deletePost(post: ForumPostEntity) {
        forumPostDao.deletePost(post)
    }
}