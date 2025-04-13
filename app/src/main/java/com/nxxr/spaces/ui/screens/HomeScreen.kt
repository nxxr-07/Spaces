package com.nxxr.spaces.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nxxr.spaces.ui.screens.auth.AuthState
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController? = null,
    authViewModel: AuthViewModel,
    modifier: Modifier
){
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController?.navigate("login")
            else -> Unit
        }
    }

    Text("Welcome Home")
    Button(
        onClick = {
            authViewModel.signOut()
            navController?.navigate("login")
        }
    ) {
        Text("Sign Out")
    }
}
