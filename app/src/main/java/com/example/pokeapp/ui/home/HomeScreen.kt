package com.example.pokeapp.ui.home // 1. Pacote corrigido (não é .ui.ui)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// 2. Imports do tema (já estavam corretos no seu arquivo)
import com.example.pokeapp.ui.theme.MasterBallPurple
import com.example.pokeapp.ui.theme.PokeBallRed
import com.example.pokeapp.ui.theme.PokeBlueTitle
import com.example.pokeapp.ui.theme.UltraBallBlack
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import com.example.pokeapp.R // Para quando você adicionar os ícones

/**
 * Tela principal (Menu de Dificuldade).
 * Esta tela lê o UiState do HomeViewModel.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToGame: (difficulty: String) -> Unit,
    onNavigateToStats: () -> Unit
) {
    // 3. Lê o uiState (que contém dados do usuário, como isPremium)
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "POKEGSSR",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = PokeBlueTitle,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "ESCOLHA A DIFICULDADE",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botão Fácil
        DifficultyButton(
            text = "Pokeball",
            color = PokeBallRed,
            // iconRes = R.drawable.ic_pokeball,
            onClick = { onNavigateToGame("easy") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botão Médio
        DifficultyButton(
            text = "Ultraball",
            color = UltraBallBlack,
            // iconRes = R.drawable.ic_ultraball,
            onClick = { onNavigateToGame("medium") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botão Difícil
        DifficultyButton(
            text = "Masterball",
            color = MasterBallPurple,
            // iconRes = R.drawable.ic_masterball,
            onClick = { onNavigateToGame("hard") }
        )

        // TODO: Aqui você pode adicionar a lógica de Admin (Requisito)
        // if (uiState.isPremium) {
        //    AdminButton(onClick = { ... })
        // }

        Spacer(modifier = Modifier.weight(1f)) // Empurra para baixo

        // Botão de Estatísticas
        TextButton(onClick = onNavigateToStats) {
            Icon(
                Icons.Default.BarChart,
                contentDescription = "Estatísticas",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Estatísticas", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(64.dp)) // Espaço para o BottomNav
    }
}

/**
 * Composable reutilizável para os botões de dificuldade.
 */
@Composable
fun DifficultyButton(
    text: String,
    color: Color,
    // iconRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para o ícone
            // Image(painterResource(id = iconRes), ...)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}