package com.example.pokeapp.ui.forum

import com.example.pokeapp.data.local.ForumPostEntity


data class ForumUiState(
    val isLoading: Boolean = true,
    val posts: List<ForumPostEntity> = emptyList(),
    val error: String? = null,


    val showDialog: Boolean = false,
    val currentPostToEdit: ForumPostEntity? = null, // Se null, é um novo post. Se não-null, é edição.
    val dialogTitle: String = "",
    val dialogContent: String = ""
)