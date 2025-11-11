package com.example.pokeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokeapp.MainApplication
import com.example.pokeapp.di.AppViewModelFactory
import com.example.pokeapp.ui.game.GameScreen
import com.example.pokeapp.ui.home.HomeScreen
import com.example.pokeapp.ui.login.LoginScreen
import com.example.pokeapp.ui.main.MainScreen
import com.example.pokeapp.ui.register.RegisterScreen
import com.example.pokeapp.ui.stats.StatsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main/{userId}") {
        fun createRoute(userId: Long) = "main/$userId"
    }
    object Home : Screen("home")
    object Stats : Screen("stats")
    object Game : Screen("game/{difficulty}") {
        fun createRoute(difficulty: String) = "game/$difficulty"
    }
}

@Composable
fun getViewModelFactory(userId: Long? = null): ViewModelProvider.Factory {
    val application = LocalContext.current.applicationContext as MainApplication
    val appContainer = application.container
    return remember(appContainer, userId) {
        AppViewModelFactory(appContainer, userId)
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel(factory = getViewModelFactory()),
                onLoginSuccess = { userId ->
                    navController.navigate(Screen.Main.createRoute(userId)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = viewModel(factory = getViewModelFactory()),
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Main.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId")
            if (userId != null) {
                MainScreen(
                    userId = userId,
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun MainNavHost(
    navController: NavHostController,
    userId: Long,
    modifier: androidx.compose.ui.Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel(factory = getViewModelFactory(userId)),
                onNavigateToGame = { difficulty ->
                    navController.navigate(Screen.Game.createRoute(difficulty))
                },
                onNavigateToStats = {
                    navController.navigate(Screen.Stats.route)
                }
            )
        }

        // *** MUDANÇA AQUI ***
        // Adiciona a função onNavigateUp para a StatsScreen
        composable(Screen.Stats.route) {
            StatsScreen(
                viewModel = viewModel(factory = getViewModelFactory(userId)),
                onNavigateUp = { navController.navigateUp() } // Passa a ação "voltar"
            )
        }

        // *** MUDANÇA AQUI ***
        // Adiciona a função onNavigateUp para a GameScreen
        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("difficulty") { type = NavType.StringType })
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty")
            if (difficulty != null) {
                GameScreen(
                    viewModel = viewModel(factory = getViewModelFactory(userId)),
                    difficulty = difficulty,
                    onGameEnd = {
                        navController.popBackStack()
                    },
                    onNavigateUp = { navController.navigateUp() } // Passa a ação "voltar"
                )
            }
        }
    }
}