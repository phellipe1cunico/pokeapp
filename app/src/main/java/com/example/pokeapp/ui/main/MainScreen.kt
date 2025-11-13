package com.example.pokeapp.ui.main

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    userId: Long,
    onLogout: () -> Unit
) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

            BottomAppBar(
                containerColor = PokeRed
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination


                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Home.route } == true,

                    onClick = {
                        navController.navigate(Screen.Home.route) {

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) }
                )


                NavigationBarItem(
                    selected = false,
                    onClick = onLogout,
                    icon = { Icon(Icons.Default.Logout, contentDescription = "Sair", tint = Color.White) }
                )
            }
        }
    ) { paddingValues ->

        MainNavHost(
            navController = navController,
            userId = userId,
            modifier = Modifier.padding(paddingValues)
        )
    }
}