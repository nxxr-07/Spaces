package com.nxxr.spaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nxxr.spaces.R
import com.nxxr.spaces.ui.navigation.Screen

@Composable
fun MainScaffold(
    navController: NavController,
    currentRoute: String,
    onTabSelected: (Screen) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CustomBottomBar(
                selectedRoute = currentRoute,
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun CustomBottomBar(
    selectedRoute: String,
    onTabSelected: (Screen) -> Unit
) {
    val items = listOf(Screen.Home, Screen.Booking, Screen.About)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { screen ->
            IconButton(
                onClick = { onTabSelected(screen) }
            ) {
                Icon(
                    imageVector = when (screen) {
                        is Screen.Home -> Icons.Default.Home
                        is Screen.Booking -> Icons.Default.AddCircle
                        is Screen.About -> Icons.AutoMirrored.Filled.ExitToApp
                        else -> Icons.Default.Info
                    },
                    contentDescription = screen.route,
                    tint = if (screen.route == selectedRoute) colorResource(id = R.color.purple_700) else Color.Gray ,
                    modifier = Modifier.size(28.dp)
                )

            }
        }
    }
}
