package com.nxxr.spaces.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nxxr.spaces.R
import com.nxxr.spaces.ui.theme.*

@Composable
fun LoginScreen(
    navController: NavController? = null,
    authViewModel: AuthViewModel = AuthViewModel(),
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when(authState){
            is AuthState.Autheticated -> { navController?.navigate("home") }
            is AuthState.Error -> Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            is AuthState.Loading -> {  }
            else -> Unit
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBackgroundStart, DarkBackgroundEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = OnDarkBackground
            )

            Text(
                text = "Sign in to your account",
                fontSize = 16.sp,
                color = HintText,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = HintText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InputBackground, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = BorderDark,
                    cursorColor = Primary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = HintText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InputBackground, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = BorderDark,
                    cursorColor = Primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.login(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {

                if ( authState == AuthState.Loading){
                    LoadingIcon()
                }else{
                    Text(text = "Login", fontSize = 18.sp, color = ButtonText)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google icon"
                )
                Text("Continue with Google", fontSize = 16.sp, color = ButtonText)
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = {
                navController?.navigate("signup")
            }) {
                Text(
                    text = "Don't have an account? Sign up",
                    color = HintText,
                    textAlign = TextAlign.Center
                )
            }


        }
    }
}

@Composable
fun LoadingIcon() {
    CircularProgressIndicator(
        modifier = Modifier,
        color = Color.White
    )
}

@Preview
@Composable
fun LoadingIconPreview(){
    LoadingIcon()
}

