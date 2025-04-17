package com.nxxr.spaces.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nxxr.spaces.R
import kotlinx.coroutines.delay

@Composable
fun PomodoroTimer(
    totalTime: Long = 25 * 60 * 1000L, // 25 mins
    modifier: Modifier = Modifier
) {
    var timeLeft by remember { mutableStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isRunning && timeLeft > 0L) {
                delay(1000L)
                timeLeft -= 1000L
            }
            if (timeLeft <= 0L) {
                isRunning = false
                Toast.makeText(context, "Pomodoro Completed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val progress = 1f - (timeLeft.toFloat() / totalTime)
    val minutes = (timeLeft / 1000) / 60
    val seconds = (timeLeft / 1000) % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // CIRCULAR CLOCK
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(200.dp)
            ) {
                // Background circle
                CircularProgressIndicator(
                    progress = 1f,
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 16.dp,
                    color = Color.DarkGray.copy(alpha = 0.3f) // Background ring
                )

                // Foreground progress
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 16.dp,
                    color = Color(0xFFFFC107) // Amber or any vibrant color
                )
                Text(
                    text = formattedTime,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Buttons
            Row {
                Button(
                    onClick = {
                        isRunning = !isRunning
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if (isRunning) Color.Red else colorResource(id = R.color.purple_500))
                ) {
                    Text(if (isRunning) "Pause" else "Start")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        isRunning = false
                        timeLeft = totalTime
                    }
                ) {
                    Text("Reset")
                }
            }
        }
    }
}
