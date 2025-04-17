package com.nxxr.spaces.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nxxr.spaces.R
import com.nxxr.spaces.ui.navigation.Screen
import com.nxxr.spaces.ui.screens.MainScaffold
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val currentRoute = Screen.Home.route

    MainScaffold(
        navController = navController,
        currentRoute = currentRoute,
        onTabSelected = { screen ->
            navController.navigate(screen.route) {
                popUpTo(Screen.Home.route)
                launchSingleTop = true
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            OccupancyGauge(
                modifier = modifier,
                occupancyPercentage = 0.7f,
                backgroundColor = colorResource(R.color.purple_700),
                filledColor = colorResource(R.color.purple_200)
            )

            Divider(modifier = Modifier.padding(16.dp), color = Color.White, thickness = 2.dp )

            Text("Pomodoro Timer 🍅", fontSize = 24.sp, color = Color.White, modifier = Modifier.padding(16.dp))

            PomodoroTimer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )


        }
    }
}



