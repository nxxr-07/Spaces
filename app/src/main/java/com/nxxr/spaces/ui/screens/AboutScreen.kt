package com.nxxr.spaces.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nxxr.spaces.R
import com.nxxr.spaces.ui.navigation.MainScaffold
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                AboutUser(
                    authViewModel = authViewModel,
                    onSignOut = { onSignOut(navController, authViewModel) }
                )
                HorizontalDivider(
                    modifier = Modifier.height(16.dp),
                    color = MaterialTheme.colorScheme.outline
                )

                DeveloperInfo()
            }
        }
    }
}

fun onSignOut(
    navController: NavController? = null,
    authViewModel: AuthViewModel
) {
    authViewModel.signOut()
    navController?.navigate("login")
}

@Composable
fun AboutUser(
    authViewModel: AuthViewModel,
    onSignOut: () -> Unit
) {
    val userDetails = authViewModel.getCurrentUserDetails()
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_new),
            contentDescription = "User Profile Icon",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(colors.surfaceVariant),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = userDetails[0],
                fontSize = 20.sp,
                color = colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = userDetails[1],
                fontSize = 14.sp,
                color = colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { onSignOut() },
                modifier = Modifier.height(36.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, colors.error),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text("Sign Out", color = colors.error, fontSize = 14.sp)
            }
        }
    }
}

@Preview
@Composable
fun DeveloperInfo(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Developers", fontSize = 36.sp, fontWeight = FontWeight.Bold)

        DevProfile("Arshnoor", painterResource(id = R.drawable.arshn))
        DevProfile("Satenderjeet", painterResource(id = R.drawable.satinder))
        DevProfile("Gurjashan", painterResource(id = R.drawable.gurjashan))
    }
}

@Composable
fun DevProfile(
    DevName: String,
    ImageRes: Painter
){
    Spacer(modifier = Modifier.height(8.dp))
    Image(
        painter = ImageRes,
        contentDescription = "Developer Profile Icon",
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(DevName, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic)
}


@Preview
@Composable
fun AboutScreenPreview() {
    AboutUser(authViewModel = AuthViewModel()) {}
}
