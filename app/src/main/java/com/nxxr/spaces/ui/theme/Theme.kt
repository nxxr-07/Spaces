package com.nxxr.spaces.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Theme Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    error = Error,
)

// Dark Theme Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color(0xFFF2F2F7),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFEBEBF5),
    error = Error
)

@Composable
fun SpacesTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
