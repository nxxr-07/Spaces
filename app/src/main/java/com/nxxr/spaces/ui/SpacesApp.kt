package com.nxxr.spaces.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nxxr.spaces.ui.navigation.AppNavGraph
import com.nxxr.spaces.ui.screens.auth.AuthViewModel

@Composable
fun SpacesApp(modifier: Modifier) {

    val authViewModel = AuthViewModel()

    AppNavGraph(
        modifier,
        authViewModel = authViewModel
    )
}