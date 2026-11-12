package com.example.geojournal.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBackClick: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // These settings are only placeholders for now.
            // The real ones will be added in the next sprints.
            SettingRow(
                title = "App lock",
                description = "Fingerprint or face lock (coming in Sprint 4)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingRow(
                title = "Backup",
                description = "Online backup of memories (coming in Sprint 4)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingRow(
                title = "Export and delete data",
                description = "Control your own data (coming in Sprint 5)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingRow(
                title = "About",
                description = "GeoJournal version 0.1 (Sprint 1)"
            )
        }
    }
}

@Composable
fun SettingRow(title: String, description: String) {
    Column {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}