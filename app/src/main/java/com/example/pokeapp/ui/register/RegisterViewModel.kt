package com.example.pokeapp.ui.register

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


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    fun updateUsername(v: String) = _uiState.update { it.copy(username = v) }
    fun updatePassword(v: String) = _uiState.update { it.copy(password = v) }
    fun updateEmail(v: String) = _uiState.update { it.copy(email = v) }
    fun updateIsPremium(v: Boolean) = _uiState.update { it.copy(isPremium = v) }


    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }


            val user = UserEntity(
                username = _uiState.value.username,
                passwordHash = _uiState.value.password,
                email = _uiState.value.email,
                isPremium = _uiState.value.isPremium
            )


            val result = userRepository.registerUser(user)


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