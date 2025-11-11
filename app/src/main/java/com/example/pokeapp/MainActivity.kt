package com.example.pokeapp // 1. Pacote corrigido (raiz)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// 2. Imports corrigidos (apontam para a pasta 'ui')
import com.example.pokeapp.ui.navigation.AppNavigation
import com.example.pokeapp.ui.theme.PokeAppTheme

/**
 * A Activity principal e ponto de entrada do aplicativo.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAppTheme {
                // Inicia o NavHost
                AppNavigation()
            }
        }
    }
}