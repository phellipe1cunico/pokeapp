package com.example.pokeapp.ui.home // 1. Pacote corrigido (não é .ui.ui)

/**
 * Representa o estado da tela Home (Menu Principal).
 */
data class HomeUiState(
    val username: String = "",
    val isPremium: Boolean = false, // Para a lógica de Admin
    val isLoading: Boolean = true,
    val error: String? = null
)