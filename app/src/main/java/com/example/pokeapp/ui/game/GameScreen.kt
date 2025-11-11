package com.example.pokeapp.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
// Import para o ícone de "voltar"
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokeapp.ui.theme.MasterBallPurple
import com.example.pokeapp.ui.theme.PokeBallRed
import com.example.pokeapp.ui.theme.UltraBallBlack
import com.example.pokeapp.ui.theme.BackgroundGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    difficulty: String,
    onGameEnd: () -> Unit,
    onNavigateUp: () -> Unit // Recebe a ação "voltar"
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = difficulty) {
        viewModel.loadNewPokemon(difficulty)
    }

    val (barColor, buttonColor) = when (difficulty) {
        "easy" -> PokeBallRed to PokeBallRed
        "medium" -> UltraBallBlack to UltraBallBlack
        "hard" -> MasterBallPurple to MasterBallPurple
        else -> Color.Gray to Color.Gray
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(difficulty.replaceFirstChar { it.uppercase() }) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = barColor,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White // Cor da seta
            ),
            // Adiciona o ícone de "voltar"
            navigationIcon = {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 64.dp))
            } else if (uiState.error != null) {
                Text("Erro: ${uiState.error}", color = Color.Red)
            } else if (uiState.currentPokemon != null) {
                GameContent(
                    uiState = uiState,
                    viewModel = viewModel,
                    buttonColor = buttonColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameContent(
    uiState: GameUiState,
    viewModel: GameViewModel,
    buttonColor: Color
) {
    Text(
        text = "ADVINHE O POKÉMON!",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 16.dp)
    )

    AsyncImage(
        model = uiState.currentPokemon?.imageUrl,
        contentDescription = "Pokémon",
        modifier = Modifier.size(250.dp).padding(16.dp),
        contentScale = ContentScale.Fit
    )

    Spacer(modifier = Modifier.height(16.dp))

    GameTextField(
        value = uiState.nameGuess,
        onValueChange = viewModel::updateNameGuess,
        label = "Nome do Pokémon",
        color = buttonColor
    )

    if (uiState.difficulty == "medium" || uiState.difficulty == "hard") {
        Spacer(modifier = Modifier.height(16.dp))
        GameTextField(
            value = uiState.typeGuess,
            onValueChange = viewModel::updateTypeGuess,
            label = "Tipo",
            color = buttonColor
        )
    }

    if (uiState.difficulty == "hard") {
        Spacer(modifier = Modifier.height(16.dp))
        GameTextField(
            value = uiState.regionGuess,
            onValueChange = viewModel::updateRegionGuess,
            label = "Região (ex: generation-i)",
            color = buttonColor
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    Button(
        onClick = viewModel::submitGuess,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text("Enviar", fontSize = 18.sp)
    }

    uiState.lastGuessResult?.let {
        val text = if (it) "Correto!" else "Errado!"
        val color = if (it) Color(0xFF4CAF50) else Color.Red
        Text(text, color = color, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    color: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BackgroundGray,
            unfocusedContainerColor = BackgroundGray,
            disabledContainerColor = BackgroundGray,
            focusedIndicatorColor = color,
            unfocusedIndicatorColor = BackgroundGray
        )
    )
}