package com.example.pokeapp.data.remotelogic

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<PokemonResponse>

    @GET
    suspend fun getPokemonSpecies(@Url url: String): Response<PokemonSpeciesResponse>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}