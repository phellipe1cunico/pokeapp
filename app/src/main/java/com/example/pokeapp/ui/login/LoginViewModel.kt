package com.example.pokeapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.repository.ResultWrapper
import com.example.pokeapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }


    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }


    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = userRepository.login(_uiState.value.username, _uiState.value.password)

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