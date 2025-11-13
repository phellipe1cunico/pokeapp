package com.example.pokeapp.di

import android.content.Context
import com.example.pokeapp.data.local.AppDatabase
import com.example.pokeapp.data.remotelogic.PokeApiService
import com.example.pokeapp.data.repository.ForumRepository
import com.example.pokeapp.data.repository.GameRepository
import com.example.pokeapp.data.repository.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Interface para o contêiner de dependências.
 */
interface AppContainerInterface {
    val userRepository: UserRepository
    val gameRepository: GameRepository
    val forumRepository: ForumRepository // 1. ADICIONADO
}


class AppContainer(context: Context) : AppContainerInterface {


    private val database by lazy { AppDatabase.getDatabase(context) }


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(PokeApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val pokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }



    override val userRepository: UserRepository by lazy {
        UserRepository(database.userDao())
    }

    override val gameRepository: GameRepository by lazy {
        GameRepository(pokeApiService, database.gameAttemptDao())
    }


    override val forumRepository: ForumRepository by lazy {
        ForumRepository(database.forumPostDao())
    }
}