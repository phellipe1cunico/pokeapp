package com.example.pokeapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapp.ui.game.GameViewModel
import com.example.pokeapp.ui.home.HomeViewModel
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.register.RegisterViewModel
import com.example.pokeapp.ui.stats.StatsViewModel

/**
 * Factory (Fábrica) para criar instâncias de ViewModel.
 * Isso cumpre o requisito de "Injeção" usando uma ViewModelFactory.
 * Ela permite que os ViewModels recebam dependências (como repositórios)
 * em seus construtores.
 */
class AppViewModelFactory(
    // Depende da abstração (Interface) e não da implementação concreta
    private val appContainer: AppContainerInterface,
    private val currentUserId: Long? // ID do usuário logado
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Se a UI pedir um LoginViewModel...
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                // ...crie um, injetando o userRepository.
                LoginViewModel(appContainer.userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(appContainer.userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                // ViewModels que precisam do ID do usuário logado
                if (currentUserId == null) throw IllegalArgumentException("HomeViewModel requer um userId")
                HomeViewModel(appContainer.userRepository, currentUserId) as T
            }
            modelClass.isAssignableFrom(GameViewModel::class.java) -> {
                if (currentUserId == null) throw IllegalArgumentException("GameViewModel requer um userId")
                GameViewModel(appContainer.gameRepository, currentUserId) as T
            }
            modelClass.isAssignableFrom(StatsViewModel::class.java) -> {
                if (currentUserId == null) throw IllegalArgumentException("StatsViewModel requer um userId")
                StatsViewModel(appContainer.gameRepository, currentUserId) as T
            }
            // Se um ViewModel desconhecido for solicitado, falhe
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}