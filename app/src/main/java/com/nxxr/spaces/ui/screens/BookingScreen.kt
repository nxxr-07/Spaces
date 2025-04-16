package com.nxxr.spaces.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nxxr.spaces.ui.navigation.Screen
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@Composable
fun BookingScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
){
    val currentRoute = Screen.Booking.route

    MainScaffold(
        navController = navController,
        currentRoute = currentRoute,
        onTabSelected = { screen ->
            navController.navigate(screen.route) {
                popUpTo(Screen.Home.route)
                launchSingleTop = true
            }
        }
    ) { padding ->
        DemoPreview()
    }
}
