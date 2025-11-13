package com.example.pokeapp.data.repository

import com.example.pokeapp.data.local.UserDao
import com.example.pokeapp.data.local.UserEntity


class UserRepository(private val userDao: UserDao) {


    suspend fun registerUser(user: UserEntity): ResultWrapper<Unit> {
        return try {

            userDao.insertUser(user)
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {

            ResultWrapper.Error("Usuário já pode existir ou erro no DB: ${e.message}")
        }
    }


    suspend fun login(username: String, passwordHash: String): ResultWrapper<UserEntity> {
        return try {
            val user = userDao.getUserByUsername(username)

            if (user != null && user.passwordHash == passwordHash) {
                ResultWrapper.Success(user)
            } else {
                ResultWrapper.Error("Usuário ou senha inválidos.")
            }
        } catch (e: Exception) {
            ResultWrapper.Error("Erro no banco de dados: ${e.message}")
        }
    }


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