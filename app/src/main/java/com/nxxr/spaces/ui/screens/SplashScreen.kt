package com.nxxr.spaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nxxr.spaces.R
import com.nxxr.spaces.ui.navigation.Screen


@Composable
fun SplashScreen(modifier: Modifier, onNavigateToLogin: () -> Unit) {

        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.study_room_splash),
                contentDescription = "Splash Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Text overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Spaces",
                    color = Color.White,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Your Space to Focus",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            Button(
                onClick = { onNavigateToLogin() },
                modifier = Modifier.padding(top = 32.dp)
                    .padding(20.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = "Get Started")
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(
    modifier: Modifier = Modifier
){
    SplashScreen(modifier) {}
}
