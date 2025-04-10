package com.nxxr.spaces.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nxxr.spaces.ui.screens.SplashScreen
import com.nxxr.spaces.ui.screens.auth.LoginScreen
import com.nxxr.spaces.ui.screens.auth.SignupScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
}

@Composable
fun AppNavGraph(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                modifier = modifier
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(onNavigateToSignup = {
                navController.navigate(Screen.Signup.route)
            })
        }
        composable(Screen.Signup.route) {
            SignupScreen(onNavigateToLogin = {
                navController.popBackStack()
            })
        }
    }
}
