package com.example.pokeapp.data.repository

import com.example.pokeapp.data.local.UserDao
import com.example.pokeapp.data.local.UserEntity

// ESTA É A VERSÃO COMPLETA (NÃO O PLACEHOLDER)
class UserRepository(private val userDao: UserDao) {

    // Requisito: CRUD (Create)
    suspend fun registerUser(user: UserEntity): ResultWrapper<Unit> {
        return try {
            // Em um app real, aqui você faria o hash da senha
            userDao.insertUser(user)
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            // Captura erros, ex: usuário com mesmo nome
            ResultWrapper.Error("Usuário já pode existir ou erro no DB: ${e.message}")
        }
    }

    // Requisito: CRUD (Read)
    suspend fun login(username: String, passwordHash: String): ResultWrapper<UserEntity> {
        return try {
            val user = userDao.getUserByUsername(username)
            // Em um app real, você compararia o hash da senha
            if (user != null && user.passwordHash == passwordHash) {
                ResultWrapper.Success(user)
            } else {
                ResultWrapper.Error("Usuário ou senha inválidos.")
            }
        } catch (e: Exception) {
            ResultWrapper.Error("Erro no banco de dados: ${e.message}")
        }
    }

    // Usado pelo HomeViewModel
    suspend fun getUser(userId: Long): ResultWrapper<UserEntity> {
        return try {
            val user = userDao.getUserById(userId)
            if (user != null) {
                ResultWrapper.Success(user)
            } else {
                ResultWrapper.Error("Usuário não encontrado.")
            }
        } catch (e: Exception) {
            ResultWrapper.Error("Erro no banco de dados: ${e.message}")
        }
    }
}