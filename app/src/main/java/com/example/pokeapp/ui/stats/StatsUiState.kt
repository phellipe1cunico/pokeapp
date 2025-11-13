package com.example.pokeapp.ui.stats


import com.example.pokeapp.data.local.GameAttemptEntity


data class StatsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,


    val attempts: List<GameAttemptEntity> = emptyList(),


    val totalPlayed: Int = 0,
    val totalSuccess: Int = 0,
    val totalFailure: Int = 0
)