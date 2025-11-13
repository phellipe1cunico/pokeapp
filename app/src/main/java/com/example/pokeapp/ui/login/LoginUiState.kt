package com.example.pokeapp.ui.login


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Long? = null,
    val error: String? = null
)