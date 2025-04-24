package com.nxxr.spaces.ui.screens.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nxxr.spaces.ui.navigation.Screen
import com.nxxr.spaces.ui.navigation.MainScaffold
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
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
            modifier = modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                RoomDetails()
                SeatsGridScreen(viewModel = SeatsViewModel())
            }
        }
    }
}

@Preview
@Composable
fun RoomDetails() {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Study Room CC1",
            color = colorScheme.onSurface,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.sp
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = { /* Room Info Action */ },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Room Info Icon",
                tint = colorScheme.onSurface
            )
        }
    }
}
