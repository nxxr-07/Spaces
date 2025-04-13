package com.nxxr.spaces.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nxxr.spaces.ui.screens.HomeScreen
import com.nxxr.spaces.ui.screens.SplashScreen
import com.nxxr.spaces.ui.screens.auth.AuthViewModel
import com.nxxr.spaces.ui.screens.auth.LoginScreen
import com.nxxr.spaces.ui.screens.auth.SignupScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
}

@Composable
fun AppNavGraph(modifier: Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination =Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navController,
                modifier = modifier
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navController,
                authViewModel,
                modifier = modifier
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                navController,
                authViewModel,
                modifier = modifier
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController,
                authViewModel,
                modifier = modifier
            )
        }
    }
}
