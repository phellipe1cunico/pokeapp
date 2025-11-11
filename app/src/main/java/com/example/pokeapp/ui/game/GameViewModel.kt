package com.example.pokeapp.ui.game // 1. Pacote corrigido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 2. Imports corrigidos (para o repositório e entidade reais)
import com.example.pokeapp.data.local.GameAttemptEntity
import com.example.pokeapp.data.repository.GameRepository
import com.example.pokeapp.data.repository.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de Jogo.
 * Esta é a versão completa que se conecta ao GameRepository.
 */
class GameViewModel(
    private val gameRepository: GameRepository,
    private val userId: Long
) : ViewModel() {

    // 3. Usa o GameUiState que definimos
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    /**
     * Carrega um novo Pokémon aleatório da API.
     */
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

            // Chama o repositório (que chama a API)
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

    // Funções de atualização dos inputs (chamadas pela UI)
    fun updateNameGuess(v: String) = _uiState.update { it.copy(nameGuess = v) }
    fun updateTypeGuess(v: String) = _uiState.update { it.copy(typeGuess = v) }
    fun updateRegionGuess(v: String) = _uiState.update { it.copy(regionGuess = v) }

    /**
     * Verifica o palpite do usuário contra a resposta correta.
     */
    fun submitGuess() {
        val state = _uiState.value
        val pokemon = state.currentPokemon ?: return

        var isCorrect = false

        // Lógica de verificação (requisito de negócio)
        // Compara ignorando maiúsculas/minúsculas
        val nameMatch = state.nameGuess.equals(pokemon.name, ignoreCase = true)

        when (state.difficulty) {
            "easy" -> {
                isCorrect = nameMatch
            }
            "medium" -> {
                // Verifica se o palpite do tipo está em QUALQUER lugar da lista de tipos
                val typeMatch = pokemon.types.any { it.equals(state.typeGuess, ignoreCase = true) }
                isCorrect = nameMatch && typeMatch
            }
            "hard" -> {
                val typeMatch = pokemon.types.any { it.equals(state.typeGuess, ignoreCase = true) }
                // A API retorna "generation-i", "generation-ii", etc.
                val regionMatch = state.regionGuess.equals(pokemon.region, ignoreCase = true)
                isCorrect = nameMatch && typeMatch && regionMatch
            }
        }

        // Atualiza a UI para mostrar o feedback (Correto/Errado)
        _uiState.update { it.copy(lastGuessResult = isCorrect) }

        // Salva a tentativa no banco de dados (Room)
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

        // Carrega o próximo Pokémon automaticamente após um curto delay
        viewModelScope.launch {
            kotlinx.coroutines.delay(1500) // 1.5 segundos para o usuário ver o feedback
            loadNewPokemon(state.difficulty) // Carrega o próximo
        }
    }
}