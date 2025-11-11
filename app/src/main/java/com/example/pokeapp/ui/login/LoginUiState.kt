package com.example.pokeapp.ui.login // 1. Pacote corrigido (não é .ui.ui)

/**
 * Representa o estado da tela de Login.
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Long? = null, // Armazena o ID do usuário se o login for bem-sucedido
    val error: String? = null // Armazena mensagens de erro
)