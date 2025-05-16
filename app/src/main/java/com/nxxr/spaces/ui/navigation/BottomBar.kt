package com.nxxr.spaces.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

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
                onTabSelected = onTabSelected,
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
    val colorScheme = MaterialTheme.colorScheme
    val items = listOf(Screen.Home, Screen.Booking, Screen.About)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(50),
                clip = false
            )
            .background(
                color = colorScheme.surface
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { screen ->
            val isSelected = screen.route == selectedRoute
            IconButton(
                onClick = { onTabSelected(screen) },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isSelected) colorScheme.primary else colorScheme.surface
                    )
            ) {
                Icon(
                    imageVector = when (screen) {
                        is Screen.Home -> Icons.Default.Home
                        is Screen.Booking -> Icons.Default.DateRange
                        is Screen.About -> Icons.Default.AccountCircle
                        else -> Icons.Default.Info
                    },
                    contentDescription = screen.route,
                    tint = if (isSelected) colorScheme.onPrimary else colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    CustomBottomBar(
        selectedRoute = Screen.Home.route,
        onTabSelected = {}
    )
}
