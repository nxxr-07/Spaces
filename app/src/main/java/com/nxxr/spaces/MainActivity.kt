package com.nxxr.spaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp
import com.nxxr.spaces.ui.SpacesApp
import com.nxxr.spaces.ui.theme.SpacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        setContent {
            SpacesTheme (){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        SpacesApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
