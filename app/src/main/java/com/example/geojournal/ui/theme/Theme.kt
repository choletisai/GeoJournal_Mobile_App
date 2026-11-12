package com.example.geojournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    secondary = GreenSecondary,
    background = LightBackground,
    surface = LightSurface
)

private val DarkColors = darkColorScheme(
    primary = GreenPrimaryDark,
    onPrimary = Color.Black,
    secondary = GreenSecondaryDark,
    background = DarkBackground,
    surface = DarkSurface
)

@Composable
fun GeoJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // use dark colours if the phone is in dark mode
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}