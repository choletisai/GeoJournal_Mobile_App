package com.example.geojournal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    // This runs once when the screen appears.
    // It waits 2 seconds and then tells the navigation to go to Home.
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // a place icon is used as the logo for now
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "GeoJournal logo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "GeoJournal",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your private place diary",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}