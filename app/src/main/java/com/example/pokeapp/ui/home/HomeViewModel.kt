package com.example.pokeapp.ui.home // 1. Pacote corrigido (não é .ui.ui)

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 2. Imports corrigidos (para o repositório e UiState reais)
import com.example.pokeapp.data.repository.ResultWrapper
import com.example.pokeapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela Home (Menu Principal).
 * Responsável por carregar os dados do usuário (ex: nome, se é premium).
 */
class HomeViewModel(
    private val userRepository: UserRepository,
    private val userId: Long
) : ViewModel() {

    // 3. Usa o HomeUiState que definimos
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Carrega os dados do usuário assim que o ViewModel é criado
        loadUserData()
    }

    /**
     * Busca os dados do usuário no repositório (que vem do Room).
     * Requisito: Lógica de Separação de UI (Admin/Premium).
     */
    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Chama o repositório real
            when (val result = userRepository.getUser(userId)) {
                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            username = result.data.username,
                            // A UI pode usar isso para mostrar/ocultar coisas
                            isPremium = result.data.isPremium
                        )
                    }
                }
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}