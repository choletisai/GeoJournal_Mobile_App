package com.example.geojournal.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.geojournal.data.SampleData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(entryId: Int, onBackClick: () -> Unit) {

    // look for the entry with the id we received.
    // It can be null if the id does not exist.
    val entry = SampleData.entries.find { it.id == entryId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memory Details") },
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
                .verticalScroll(rememberScrollState())
        ) {

            if (entry == null) {
                // no crash if the id is wrong, we just show a message
                Text(
                    text = "Sorry, this entry could not be found.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Place", style = MaterialTheme.typography.labelLarge)
                Text(text = entry.place, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Date", style = MaterialTheme.typography.labelLarge)
                Text(text = entry.date, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Weather", style = MaterialTheme.typography.labelLarge)
                Text(text = entry.weather, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Note", style = MaterialTheme.typography.labelLarge)
                Text(text = entry.note, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}