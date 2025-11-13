package com.example.pokeapp.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.pokeapp.data.local.GameAttemptEntity
import com.example.pokeapp.data.repository.GameRepository
import com.example.pokeapp.data.repository.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class GameViewModel(
    private val gameRepository: GameRepository,
    private val userId: Long
) : ViewModel() {


    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    fun loadNewPokemon(difficulty: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    difficulty = difficulty,
                    lastGuessResult = null,
                    nameGuess = "",
                    typeGuess = "",
                    regionGuess = ""
                )
            }


            when (val result = gameRepository.getRandomPokemon()) {
                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(isLoading = false, currentPokemon = result.data) }
                }
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }


    fun updateNameGuess(v: String) = _uiState.update { it.copy(nameGuess = v) }
    fun updateTypeGuess(v: String) = _uiState.update { it.copy(typeGuess = v) }
    fun updateRegionGuess(v: String) = _uiState.update { it.copy(regionGuess = v) }


    fun submitGuess() {
        val state = _uiState.value
        val pokemon = state.currentPokemon ?: return

        var isCorrect = false


        val nameMatch = state.nameGuess.equals(pokemon.name, ignoreCase = true)

        when (state.difficulty) {
            "easy" -> {
                isCorrect = nameMatch
            }
            "medium" -> {

                val typeMatch = pokemon.types.any { it.equals(state.typeGuess, ignoreCase = true) }
                isCorrect = nameMatch && typeMatch
            }
            "hard" -> {
                val typeMatch = pokemon.types.any { it.equals(state.typeGuess, ignoreCase = true) }

                val regionMatch = state.regionGuess.equals(pokemon.region, ignoreCase = true)
                isCorrect = nameMatch && typeMatch && regionMatch
            }
        }


        _uiState.update { it.copy(lastGuessResult = isCorrect) }


        viewModelScope.launch {
            gameRepository.saveAttempt(
                GameAttemptEntity(
                    userId = userId,
                    pokemonName = pokemon.name,
                    wasSuccess = isCorrect,
                    difficulty = state.difficulty
                )
            )
        }


        viewModelScope.launch {
            kotlinx.coroutines.delay(1500)
            loadNewPokemon(state.difficulty)
        }
    }
}