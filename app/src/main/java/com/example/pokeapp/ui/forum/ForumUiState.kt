package com.example.pokeapp.ui.forum

import com.example.pokeapp.data.local.ForumPostEntity

/**
 * Representa o estado da tela do Fórum.
 */
data class ForumUiState(
    val isLoading: Boolean = true,
    val posts: List<ForumPostEntity> = emptyList(),
    val error: String? = null,

    // Controle do Dialog de Adicionar/Editar
    val showDialog: Boolean = false,
    val currentPostToEdit: ForumPostEntity? = null, // Se null, é um novo post. Se não-null, é edição.
    val dialogTitle: String = "",
    val dialogContent: String = ""
)