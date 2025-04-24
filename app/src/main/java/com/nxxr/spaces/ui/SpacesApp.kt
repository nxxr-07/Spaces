package com.nxxr.spaces.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nxxr.spaces.ui.navigation.AppNavGraph
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SpacesApp(modifier: Modifier) {

    val authViewModel = AuthViewModel()

    AppNavGraph(
        modifier,
        authViewModel = authViewModel
    )
}