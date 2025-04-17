package com.nxxr.spaces.ui.screens.booking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nxxr.spaces.ui.navigation.Screen
import com.nxxr.spaces.ui.screens.MainScaffold
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
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            SeatsGridScreen(viewModel = SeatsViewModel())
        }
    }
}
