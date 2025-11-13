package com.example.pokeapp.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 2. Imports corrigidos
import com.example.pokeapp.data.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class StatsViewModel(
    gameRepository: GameRepository,
    userId: Long
) : ViewModel() {


    val uiState: StateFlow<StatsUiState> = gameRepository.getGameHistory(userId)
        .map { attempts ->

            StatsUiState(
                isLoading = false,
                attempts = attempts, // Lista para o LazyColumn
                totalPlayed = attempts.size,
                totalSuccess = attempts.count { it.wasSuccess },
                totalFailure = attempts.count { !it.wasSuccess }
            )
        }

        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mantém o flow ativo por 5s
            initialValue = StatsUiState(isLoading = true) // Estado inicial
        )
}