package com.example.pokeapp.ui.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// Imports para o ícone de "voltar" e Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.pokeapp.data.local.GameAttemptEntity
import com.example.pokeapp.ui.theme.PokeRed // Import para a cor da barra

/**
 * Tela de Estatísticas.
 * Mostra um resumo e um histórico (LazyColumn) das tentativas.
 */
@OptIn(ExperimentalMaterial3Api::class) // Necessário para Scaffold e TopAppBar
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // Para o padding do Scaffold
@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
    onNavigateUp: () -> Unit // *** MUDANÇA AQUI: Recebe a ação "voltar" ***
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            // *** MUDANÇA AQUI: Adiciona uma TopAppBar ***
            TopAppBar(
                title = { Text("Estatísticas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokeRed, // Cor da barra
                    titleContentColor = Color.White, // Cor do título
                    navigationIconContentColor = Color.White // Cor da seta
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) { // Chama a ação "voltar"
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues -> // O conteúdo da tela agora fica dentro do Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplica o padding da TopAppBar
                .padding(16.dp) // Adiciona o padding original da tela
        ) {

            // O conteúdo original da tela começa aqui
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.error != null) {
                Text("Erro: ${uiState.error}", color = Color.Red)
            } else {
                StatsSummary(uiState)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Histórico de Tentativas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.attempts) { attempt ->
                        AttemptItem(attempt)
                    }
                }
            }
        }
    }
}

/**
 * Composable para o card de resumo (Jogos, Acertos, Erros).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsSummary(uiState: StatsUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatBox(label = "Jogos", value = uiState.totalPlayed.toString())
            StatBox(label = "Acertos", value = uiState.totalSuccess.toString(), color = Color(0xFF4CAF50))
            StatBox(label = "Erros", value = uiState.totalFailure.toString(), color = Color(0xFFF44336))
        }
    }
}

/**
 * Composable para um único item de estatística (ex: "Jogos: 5").
 */
@Composable
fun StatBox(label: String, value: String, color: Color = Color.Black) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 16.sp, color = Color.Gray)
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

/**
 * Composable para um único item no LazyColumn do histórico.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttemptItem(attempt: GameAttemptEntity) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (attempt.wasSuccess) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = attempt.pokemonName.replaceFirstChar { it.uppercase() },
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = attempt.difficulty,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = if (attempt.wasSuccess) "Acertou" else "Errou",
                color = if (attempt.wasSuccess) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
    }
}