package com.example.pokeapp.ui.game
import com.example.pokeapp.data.repository.PokemonData


data class GameUiState(
    val difficulty: String = "easy",
    val isLoading: Boolean = true,
    val error: String? = null,
    val currentPokemon: PokemonData? = null,


    val nameGuess: String = "",
    val typeGuess: String = "",
    val regionGuess: String = "",


    val lastGuessResult: Boolean? = null
)