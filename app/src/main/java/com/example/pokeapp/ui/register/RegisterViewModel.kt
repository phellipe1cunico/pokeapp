package com.example.pokeapp.ui.register // 1. Pacote corrigido (não é .ui.ui)

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 2. Imports corrigidos (para o repositório e entidade reais)
import com.example.pokeapp.data.local.UserEntity
import com.example.pokeapp.data.repository.ResultWrapper
import com.example.pokeapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de Cadastro.
 * Esta é a versão completa que se conecta ao UserRepository.
 */
class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // 3. Usa o RegisterUiState que acabamos de salvar
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // Funções de atualização dos inputs (chamadas pela UI)
    fun updateUsername(v: String) = _uiState.update { it.copy(username = v) }
    fun updatePassword(v: String) = _uiState.update { it.copy(password = v) }
    fun updateEmail(v: String) = _uiState.update { it.copy(email = v) }
    fun updateIsPremium(v: Boolean) = _uiState.update { it.copy(isPremium = v) }

    /**
     * Tenta registrar um novo usuário chamando o repositório.
     */
    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Cria a entidade do usuário para salvar no Room
            val user = UserEntity(
                username = _uiState.value.username,
                passwordHash = _uiState.value.password, // ATENÇÃO: Use HASH em um app real!
                email = _uiState.value.email,
                isPremium = _uiState.value.isPremium
            )

            // Chama o repositório real
            val result = userRepository.registerUser(user)

            // Atualiza o UiState com base no sucesso ou falha
            when(result) {
                is ResultWrapper.Success -> {
                    _uiState.update { it.copy(isLoading = false, registerSuccess = true) }
                }
                is ResultWrapper.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}