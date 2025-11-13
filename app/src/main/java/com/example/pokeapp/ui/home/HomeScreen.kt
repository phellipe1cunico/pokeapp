package com.example.pokeapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
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


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToGame: (difficulty: String) -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToForum: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

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


        Text(
            text = "POKEGSSR",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = PokeBlueTitle,
            modifier = Modifier.padding(top = 0.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "ESCOLHA A DIFICULDADE",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(32.dp))



        DifficultyButton(
            text = "Pokeball",
            color = PokeBallRed,
            iconRes = R.drawable.pokeball,
            onClick = { onNavigateToGame("easy") }
        )
        Spacer(modifier = Modifier.height(16.dp))


        DifficultyButton(
            text = "Ultraball",
            color = UltraBallBlack,
            iconRes = R.drawable.ultraball,
            onClick = { onNavigateToGame("medium") }
        )
        Spacer(modifier = Modifier.height(16.dp))


        DifficultyButton(
            text = "Masterball",
            color = MasterBallPurple,
            iconRes = R.drawable.masterball,
            onClick = { onNavigateToGame("hard") }
        )

        Spacer(modifier = Modifier.weight(1f)) // Empurra para baixo


        TextButton(onClick = onNavigateToStats) {
            Icon(
                Icons.Default.BarChart,
                contentDescription = "Estatísticas",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Estatísticas", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}


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