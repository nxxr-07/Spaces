package com.nxxr.spaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nxxr.spaces.R
import com.nxxr.spaces.ui.navigation.Screen
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@Composable
fun AboutScreen(
    navController: NavController? = null,
    authViewModel: AuthViewModel,
) {
    val currentRoute = Screen.About.route

    if (navController != null) {
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⚙️ About Screen")
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = {
                        authViewModel.signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0)
                        }
                    }) {
                        Text("Sign Out")
                    }
                }
            }

        }
    }
}

fun onSignOut(
    navController: NavController? = null,
    authViewModel: AuthViewModel
){
    authViewModel.signOut()
    navController?.navigate("login")
}

@Composable
fun AboutUser(
    onSignOut: () -> Unit
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(24.dp)
    ){
        Image(
            painter =  painterResource(id = R.drawable.user_profile),
            contentDescription = "User Profile Icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(80.dp)
        )

        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Arshnoor", fontSize = 28.sp,  color = Color.White)

            OutlinedButton(
                onClick = { onSignOut() }
            ) {
                Text("Sign Out", color = Color.Red)
            }
        }
    }
}

