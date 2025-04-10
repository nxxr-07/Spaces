package com.nxxr.spaces.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nxxr.spaces.ui.navigation.AppNavGraph

@Composable
fun SpacesApp(modifier: Modifier) {
    AppNavGraph(modifier)
}