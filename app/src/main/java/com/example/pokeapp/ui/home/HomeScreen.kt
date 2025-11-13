package com.example.pokeapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat // 1. IMPORT ADICIONADO
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.pokeapp.ui.theme.MasterBallPurple
import com.example.pokeapp.ui.theme.PokeBallRed
import com.example.pokeapp.ui.theme.PokeBlueTitle
import com.example.pokeapp.ui.theme.UltraBallBlack
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokeapp.R

/**
 * Tela principal (Menu de Dificuldade).
 * Esta tela lê o UiState do HomeViewModel.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToGame: (difficulty: String) -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToForum: () -> Unit // 2. NOVO PARÂMETRO ADICIONADO
) {
    // 3. Lê o uiState (que contém dados do usuário, como isPremium)
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // 4. LÓGICA DO FÓRUM (INÍCIO)
        // Colocamos o ícone no canto superior direito
        Box(modifier = Modifier.fillMaxWidth()) {
            if (uiState.isPremium) {
                IconButton(
                    onClick = onNavigateToForum, // Chama a nova navegação
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Fórum Premium",
                        tint = MasterBallPurple, // Cor premium
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        // LÓGICA DO FÓRUM (FIM)

        Text(
            text = "POKEGSSR",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = PokeBlueTitle,
            // 5. Ajusta o padding para compensar o ícone
            modifier = Modifier.padding(top = 0.dp)
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
            iconRes = R.drawable.pokeball,
            onClick = { onNavigateToGame("easy") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botão Médio
        DifficultyButton(
            text = "Ultraball",
            color = UltraBallBlack,
            iconRes = R.drawable.ultraball,
            onClick = { onNavigateToGame("medium") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botão Difícil
        DifficultyButton(
            text = "Masterball",
            color = MasterBallPurple,
            iconRes = R.drawable.masterball,
            onClick = { onNavigateToGame("hard") }
        )

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
    iconRes: Int,
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

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "pokeball",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}