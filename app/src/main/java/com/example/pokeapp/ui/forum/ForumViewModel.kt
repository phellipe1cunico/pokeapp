package com.example.pokeapp.ui.forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.local.ForumPostEntity
import com.example.pokeapp.data.local.UserEntity
import com.example.pokeapp.data.repository.ForumRepository
import com.example.pokeapp.data.repository.ResultWrapper
import com.example.pokeapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForumViewModel(
    private val forumRepository: ForumRepository,
    private val userRepository: UserRepository,
    private val userId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForumUiState())
    val uiState: StateFlow<ForumUiState> = _uiState.asStateFlow()

    private var currentUser: UserEntity? = null

    init {
        loadCurrentUser()
        collectPosts()
    }

    // 1. Carrega o usuário logado para "carimbar" o nome nos posts
    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getUser(userId)) {
                is ResultWrapper.Success -> currentUser = result.data
                is ResultWrapper.Error -> _uiState.update { it.copy(error = result.message) }
            }
        }
    }

    // 2. Observa o banco de dados e atualiza a UI
    private fun collectPosts() {
        viewModelScope.launch {
            forumRepository.getAllPosts().collect { posts ->
                _uiState.update { it.copy(isLoading = false, posts = posts) }
            }
        }
    }

    // --- Funções chamadas pela UI ---

    fun onAddNewPostClick() {
        _uiState.update {
            it.copy(
                showDialog = true,
                currentPostToEdit = null, // Garante que é um "novo post"
                dialogTitle = "",
                dialogContent = ""
            )
        }
    }

    fun onEditPostClick(post: ForumPostEntity) {
        _uiState.update {
            it.copy(
                showDialog = true,
                currentPostToEdit = post, // Define o post para edição
                dialogTitle = post.title,
                dialogContent = post.content
            )
        }
    }

    fun onDeletePostClick(post: ForumPostEntity) {
        viewModelScope.launch {
            forumRepository.deletePost(post)
        }
    }

    fun onDialogDismiss() {
        _uiState.update {
            it.copy(
                showDialog = false,
                currentPostToEdit = null,
                dialogTitle = "",
                dialogContent = ""
            )
        }
    }

    // Atualiza o estado dos TextFields do dialog
    fun onDialogTitleChange(title: String) {
        _uiState.update { it.copy(dialogTitle = title) }
    }

    fun onDialogContentChange(content: String) {
        _uiState.update { it.copy(dialogContent = content) }
    }

    fun onDialogSave() {
        val user = currentUser
        if (user == null) {
            _uiState.update { it.copy(error = "Erro: Usuário não carregado.") }
            return
        }

        val state = _uiState.value
        val title = state.dialogTitle
        val content = state.dialogContent

        if (title.isBlank() || content.isBlank()) {
            _uiState.update { it.copy(error = "Título e conteúdo não podem estar vazios.") }
            return
        }

        viewModelScope.launch {
            if (state.currentPostToEdit == null) {
                // Criar Novo Post
                val newPost = ForumPostEntity(
                    userId = user.id,
                    username = user.username,
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                forumRepository.addPost(newPost)
            } else {
                // Editar Post Existente
                val updatedPost = state.currentPostToEdit.copy(
                    title = title,
                    content = content
                )
                forumRepository.updatePost(updatedPost)
            }
        }
        onDialogDismiss() // Fecha o dialog após salvar
    }
}