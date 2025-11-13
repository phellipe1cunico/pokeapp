package com.example.pokeapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "forum_posts",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE // Posts são deletados se o usuário for
    )],
    indices = [Index(value = ["userId"])]
)
data class ForumPostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val username: String, // Denormalizado para facilitar a exibição
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)