package com.example.pokeapp.ui.home


data class HomeUiState(
    val username: String = "",
    val isPremium: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)