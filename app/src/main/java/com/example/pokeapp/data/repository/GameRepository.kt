package com.example.pokeapp.data.repository

import com.example.pokeapp.data.local.GameAttemptDao
import com.example.pokeapp.data.local.GameAttemptEntity
import com.example.pokeapp.data.remotelogic.PokeApiService
// Importa o PokemonResponse da pasta remotelogic
import com.example.pokeapp.data.remotelogic.PokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

// Modelo de dados limpo para a UI (para não expor DTOs)
data class PokemonData(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val region: String // ex: "generation-i"
)

// ESTA É A VERSÃO COMPLETA (NÃO O PLACEHOLDER)
class GameRepository(
    private val pokeApiService: PokeApiService,
    private val gameAttemptDao: GameAttemptDao
) {

    // Requisito: Tratamento de Exceções
    suspend fun getRandomPokemon(): ResultWrapper<PokemonData> {
        return try {
            // Lógica real da API (Retrofit)
            val randomId = Random.nextInt(1, 1026) // A API tem ~1025 pokémons
            val pokemonResponse = pokeApiService.getPokemon(randomId)

            val pokemonBody = pokemonResponse.body()
            if (pokemonResponse.isSuccessful && pokemonBody != null) {
                // Segunda chamada para a região
                val speciesResponse = pokeApiService.getPokemonSpecies(pokemonBody.species.url)
                val speciesBody = speciesResponse.body()

                if (speciesResponse.isSuccessful && speciesBody != null) {
                    // Mapeia DTOs para o modelo de dados limpo
                    val pokemonData = PokemonData(
                        id = pokemonBody.id,
                        name = pokemonBody.name.replaceFirstChar { it.uppercase() }, // Capitaliza o nome
                        imageUrl = pokemonBody.sprites.other.officialArtwork.frontDefault,
                        types = pokemonBody.types.map { it.type.name },
                        region = speciesBody.generation.name
                    )
                    ResultWrapper.Success(pokemonData)
                } else {
                    ResultWrapper.Error("Falha ao buscar detalhes da espécie do Pokémon.")
                }
            } else {
                ResultWrapper.Error("Falha ao buscar Pokémon. Código: ${pokemonResponse.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Error("Erro de rede: ${e.message}")
        }
    }

    // --- Métodos do DAO ---

    // Requisito: CRUD (Create)
    suspend fun saveAttempt(attempt: GameAttemptEntity) {
        gameAttemptDao.insertAttempt(attempt)
    }

    // Requisito: CRUD (Read via Flow)
    fun getGameHistory(userId: Long): Flow<List<GameAttemptEntity>> {
        return gameAttemptDao.getAttemptsForUser(userId)
    }
}