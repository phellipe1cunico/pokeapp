package com.example.pokeapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_attempts",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],

    indices = [Index(value = ["userId"])]
)
data class GameAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val pokemonName: String,
    val wasSuccess: Boolean,
    val difficulty: String,
    val timestamp: Long = System.currentTimeMillis()
)