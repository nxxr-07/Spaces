package com.nxxr.spaces.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nxxr.spaces.ui.screens.AboutScreen
import com.nxxr.spaces.ui.screens.booking.BookingScreen
import com.nxxr.spaces.ui.screens.home.HomeScreen
import com.nxxr.spaces.ui.screens.SplashScreen
import com.nxxr.spaces.ui.screens.auth.AuthState
import com.nxxr.spaces.ui.screens.auth.AuthViewModel
import com.nxxr.spaces.ui.screens.auth.LoginScreen
import com.nxxr.spaces.ui.screens.auth.SignupScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object About: Screen("about")
    object Booking: Screen("booking")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(modifier: Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            if(authViewModel.authState.value == AuthState.Autheticated){
                HomeScreen(
                    navController,
                    authViewModel,
                    modifier = modifier,
                    userId = authViewModel.currentUserId()
                )
            }else{
                SplashScreen(
                    navController,
                    modifier = modifier
                )
            }

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
                modifier = modifier,
                userId = authViewModel.currentUserId()
            )
        }
        composable(Screen.About.route) {
            AboutScreen(
                navController,
                authViewModel
            )
        }
        composable(Screen.Booking.route) {
            BookingScreen(
                navController,
                authViewModel,
                modifier
            )
        }
    }
}
