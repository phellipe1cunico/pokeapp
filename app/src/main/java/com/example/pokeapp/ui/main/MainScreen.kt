package com.example.pokeapp.ui.main // 1. Pacote corrigido (não é .ui.ui)

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
// 2. Imports corrigidos (agora apontam para .ui.navigation)
import com.example.pokeapp.ui.navigation.MainNavHost
import com.example.pokeapp.ui.navigation.Screen
import com.example.pokeapp.ui.theme.PokeRed

/**
 * A tela "container" principal que hospeda o Bottom Navigation Bar
 * e o NavHost para as telas internas (Home, Stats, Game).
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    userId: Long,
    onLogout: () -> Unit
) {
    // NavController interno, apenas para o BottomNav
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            // Requisito: Bottom Navigation Bar
            BottomAppBar(
                containerColor = PokeRed
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Item "Home"
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Home.route } == true,
                    // 3. ESTA LÓGICA AGORA VAI FUNCIONAR
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            // Volta para o início do grafo (HomeScreen)
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) }
                )

                // Item "Sair"
                NavigationBarItem(
                    selected = false,
                    onClick = onLogout,
                    icon = { Icon(Icons.Default.Logout, contentDescription = "Sair", tint = Color.White) }
                )
            }
        }
    ) { paddingValues ->
        // Host de navegação para as telas internas
        MainNavHost(
            navController = navController,
            userId = userId,
            modifier = Modifier.padding(paddingValues)
        )
    }
}