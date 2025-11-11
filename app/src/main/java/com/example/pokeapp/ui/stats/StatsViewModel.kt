package com.example.pokeapp.ui.stats // 1. Pacote corrigido (não é .ui.ui)

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 2. Imports corrigidos
import com.example.pokeapp.data.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel para a tela de Estatísticas.
 * Esta é a versão completa que se conecta ao GameRepository.
 */
class StatsViewModel(
    gameRepository: GameRepository,
    userId: Long
) : ViewModel() {

    /**
     * Requisito: Reatividade (Flow) + Lógica Kotlin (map, filter, etc).
     * O UiState é um StateFlow que é derivado (via .map) do Flow do repositório.
     * A UI irá coletar este StateFlow.
     */
    val uiState: StateFlow<StatsUiState> = gameRepository.getGameHistory(userId)
        .map { attempts ->
            // 3. Lógica Kotlin (requisito: map/filter/count)
            StatsUiState(
                isLoading = false,
                attempts = attempts, // Lista para o LazyColumn
                totalPlayed = attempts.size,
                totalSuccess = attempts.count { it.wasSuccess },
                totalFailure = attempts.count { !it.wasSuccess }
            )
        }
        // Converte o Flow "frio" em um StateFlow "quente" para a UI
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mantém o flow ativo por 5s
            initialValue = StatsUiState(isLoading = true) // Estado inicial
        )
}