package com.example.pokeapp.ui.register // 1. Pacote corrigido (não é .ui.ui)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// 2. Imports corrigidos
import com.example.pokeapp.ui.theme.BackgroundGray
import com.example.pokeapp.ui.theme.PokeBlueTitle
import com.example.pokeapp.ui.theme.PokeRed

/**
 * Tela de Cadastro.
 * Requer @OptIn por usar OutlinedTextField e Checkbox.
 */
// 3. Adicionada anotação para API Experimental
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Efeito para navegar de volta quando o cadastro tiver sucesso
    LaunchedEffect(uiState.registerSuccess) {
        if (uiState.registerSuccess) {
            onRegisterSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "POKEGSSR",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = PokeBlueTitle,
                modifier = Modifier.padding(bottom = 64.dp)
            )

            // Campo Usuário
            OutlinedTextField(
                value = uiState.username,
                onValueChange = viewModel::updateUsername,
                label = { Text("Usuário") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = BackgroundGray,
                    unfocusedContainerColor = BackgroundGray,
                    disabledContainerColor = BackgroundGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Senha
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::updatePassword,
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = BackgroundGray,
                    unfocusedContainerColor = BackgroundGray,
                    disabledContainerColor = BackgroundGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Email
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = BackgroundGray,
                    unfocusedContainerColor = BackgroundGray,
                    disabledContainerColor = BackgroundGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox "Usuário Premium" (Requisito: Lógica de Admin)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.isPremium,
                    onCheckedChange = viewModel::updateIsPremium
                )
                Text("Usuário Premium (Admin)")
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Botão Cadastrar
            Button(
                onClick = viewModel::register,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PokeRed),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Cadastrar", fontSize = 18.sp)
            }

            // Exibição de Erro
            uiState.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}