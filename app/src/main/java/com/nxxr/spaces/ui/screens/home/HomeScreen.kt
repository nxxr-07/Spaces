package com.nxxr.spaces.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nxxr.spaces.R
import com.nxxr.spaces.data.model.Booking
import com.nxxr.spaces.ui.navigation.Screen
import com.nxxr.spaces.ui.navigation.MainScaffold
import com.nxxr.spaces.ui.screens.auth.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = remember { HomeViewModel() },
    userId: String
) {
    val currentRoute = Screen.Home.route
    val currentBooking by viewModel.currentBooking.collectAsState()
    val occupiedCount by viewModel.occupiedSeatsCount.collectAsState()

    var showReminder by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.fetchHomeData(userId)
        println("Current User Id: $userId")
        println("Fetched currentBooking: ${viewModel.currentBooking.value}")
    }

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
        HomeScreenLayout(
            modifier.padding(innerPadding), occupiedCount, currentBooking, showReminder
        )
    }
}

@Composable
fun HomeScreenLayout(
    modifier: Modifier,
    occupiedCount: Int,
    currentBooking: Booking?,
    showReminder: Boolean
){
    var showReminderLocal = showReminder

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OccupancyGauge(
            modifier = Modifier,
            occupancySeatCount = occupiedCount,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            filledColor = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider(
            modifier = Modifier.padding(16.dp),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Text("Focus Session", fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(16.dp))

        PomodoroTimer(
            modifier = Modifier
                .fillMaxWidth()
        )

        currentBooking?.let {
            if(showReminderLocal){
                BookingReminder(it.status, it.startTime, it.endTime, onDismiss = { showReminderLocal = false })
            }
        }

    }
}


@Composable
fun BookingReminder(
    status: String,
    startTime: Long,
    endTime: Long,
    onDismiss: () -> Unit
) {
    // Formatting timestamps into a readable date-time format
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val startFormatted = dateFormat.format(Date(startTime))
    val endFormatted = dateFormat.format(Date(endTime))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(25),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "Booking Time Icon",
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "$status Booking",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "From $startFormatted To $endFormatted",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        IconButton(
            onClick = { onDismiss() },
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Dismiss",
                tint = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Add Note",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen(){
    HomeScreenLayout(
        modifier = Modifier.background(Color.White),
        occupiedCount = 7,
        currentBooking = Booking(
            status = "Upcoming",
            startTime = 500,
            endTime = 1000
        ),
        showReminder = true
    )
}

//@Preview
//@Composable
//fun BookingReminderPreview() {
//    BookingReminder(
//        status = "Upcoming",
//        startTime = 500,
//        endTime = 1000,
//        {}
//    )
//}
