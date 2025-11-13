package com.example.pokeapp.ui.forum

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pokeapp.data.local.ForumPostEntity
import com.example.pokeapp.ui.theme.MasterBallPurple
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tela do Fórum, agora com funcionalidade de CRUD.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    viewModel: ForumViewModel, // 1. Recebe o ViewModel
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // 2. Dialog de Adicionar/Editar
    if (uiState.showDialog) {
        AddEditPostDialog(
            title = uiState.dialogTitle,
            content = uiState.dialogContent,
            onTitleChange = viewModel::onDialogTitleChange,
            onContentChange = viewModel::onDialogContentChange,
            onDismiss = viewModel::onDialogDismiss,
            onSave = viewModel::onDialogSave,
            isEditing = uiState.currentPostToEdit != null
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fórum Premium") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MasterBallPurple,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        },
        // 3. Botão (FAB) para adicionar novo post
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::onAddNewPostClick,
                containerColor = MasterBallPurple
            ) {
                Icon(Icons.Default.Add, "Novo Post", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.posts.isEmpty()) {
                Text(
                    "Nenhum post ainda. Seja o primeiro!",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 64.dp)
                )
            } else {
                // 4. Lista de Posts
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.posts) { post ->
                        ForumPostItem(
                            post = post,
                            onEdit = { viewModel.onEditPostClick(post) },
                            onDelete = { viewModel.onDeletePostClick(post) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable para um único item de post na lista.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumPostItem(
    post: ForumPostEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Formata a data e nome
            Text(
                text = "por ${post.username} em ${post.timestamp.toFormattedDate()}",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = post.content,
                fontSize = 16.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Botões de Ação (Edit/Delete)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Deletar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

/**
 * Composable para o Dialog de Adicionar/Editar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPostDialog(
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    isEditing: Boolean
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isEditing) "Editar Post" else "Novo Post",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    label = { Text("Conteúdo") },
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onSave) {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}

// Função utilitária para formatar o timestamp
private fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}