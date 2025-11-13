package com.example.pokeapp.ui.register


data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val isPremium: Boolean = false,
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)