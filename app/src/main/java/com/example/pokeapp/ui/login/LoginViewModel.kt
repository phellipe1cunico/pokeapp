package com.example.pokeapp.ui.login // 1. Pacote corrigido (não é .ui.ui)

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
 * ViewModel para a tela de Login.
 * Esta é a versão completa que se conecta ao UserRepository.
 */
class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    // 3. Usa o LoginUiState que acabamos de salvar
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Atualiza o campo 'username' no UiState.
     */
    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    /**
     * Atualiza o campo 'password' no UiState.
     */
    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    /**
     * Tenta realizar o login chamando o repositório.
     */
    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Chama o repositório real
            val result = userRepository.login(_uiState.value.username, _uiState.value.password)

            // Atualiza o UiState com base no sucesso ou falha
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, loginSuccess = result.data.id)
                    }
                }
                is ResultWrapper.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }
}