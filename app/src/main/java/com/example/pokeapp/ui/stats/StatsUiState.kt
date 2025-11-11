package com.example.pokeapp.ui.stats // 1. Pacote corrigido (não é .ui.ui)

// 2. Import corrigido para a entidade do banco de dados
import com.example.pokeapp.data.local.GameAttemptEntity

/**
 * Representa o estado da tela de Estatísticas.
 */
data class StatsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,

    // Lista de tentativas para o LazyColumn
    val attempts: List<GameAttemptEntity> = emptyList(),

    // Dados agregados (calculados no ViewModel)
    val totalPlayed: Int = 0,
    val totalSuccess: Int = 0,
    val totalFailure: Int = 0
)