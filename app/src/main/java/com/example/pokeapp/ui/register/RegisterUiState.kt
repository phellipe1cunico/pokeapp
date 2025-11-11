package com.example.pokeapp.ui.register // 1. Pacote corrigido (não é .ui.ui)

/**
 * Representa o estado da tela de Cadastro.
 */
data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val isPremium: Boolean = false, // Para o Checkbox
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false, // Para navegar de volta
    val error: String? = null
)