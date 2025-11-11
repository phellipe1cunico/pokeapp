package com.example.pokeapp.di

import android.content.Context
import com.example.pokeapp.data.local.AppDatabase
import com.example.pokeapp.data.remotelogic.PokeApiService
import com.example.pokeapp.data.repository.GameRepository
import com.example.pokeapp.data.repository.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Interface para o contêiner de dependências.
 * Isso permite que a ViewModelFactory dependa de uma abstração,
 * facilitando testes ou futuras mudanças.
 */
interface AppContainerInterface {
    val userRepository: UserRepository
    val gameRepository: GameRepository
}

/**
 * Implementação do contêiner de dependências (Service Locator).
 * Ele é responsável por criar e fornecer instâncias únicas (Singletons)
 * do banco de dados, serviço de API e repositórios.
 */
class AppContainer(context: Context) : AppContainerInterface {

    // 1. Banco de Dados (Room)
    // Cria uma instância única (lazy) do seu AppDatabase.
    private val database by lazy { AppDatabase.getDatabase(context) }

    // 2. Serviço de API (Retrofit)
    // Cria uma instância única (lazy) do Retrofit.
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(PokeApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Cria uma instância única (lazy) do serviço da API.
    private val pokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }

    // 3. Repositórios (Injetando as dependências)

    /**
     * Fornece a instância única do UserRepository,
     * injetando o UserDao (do Room).
     */
    override val userRepository: UserRepository by lazy {
        // Injeta o DAO no repositório
        UserRepository(database.userDao())
    }

    /**
     * Fornece a instância única do GameRepository,
     * injetando o PokeApiService (do Retrofit) e o GameAttemptDao (do Room).
     */
    override val gameRepository: GameRepository by lazy {
        // Injeta a API e o DAO no repositório
        GameRepository(pokeApiService, database.gameAttemptDao())
    }
}