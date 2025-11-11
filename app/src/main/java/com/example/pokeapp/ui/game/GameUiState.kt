package com.example.pokeapp.ui.game // 1. Pacote corrigido (não é .ui.ui)

// 2. Import corrigido para o modelo de dados do repositório
import com.example.pokeapp.data.repository.PokemonData

/**
 * Representa o estado da tela do Jogo.
 */
data class GameUiState(
    val difficulty: String = "easy",
    val isLoading: Boolean = true,
    val error: String? = null,
    val currentPokemon: PokemonData? = null, // O Pokémon a ser adivinhado

    // Campos de input do usuário
    val nameGuess: String = "",
    val typeGuess: String = "",
    val regionGuess: String = "",

    // Feedback para o usuário
    val lastGuessResult: Boolean? = null
)