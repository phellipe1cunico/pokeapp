package com.example.pokeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val passwordHash: String, // Em um app real, armazene um hash, não a senha
    val email: String,
    val isPremium: Boolean // Para a lógica de Admin
)