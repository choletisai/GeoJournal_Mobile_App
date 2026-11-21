package com.example.geojournal.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geojournal.model.JournalEntry
import com.example.geojournal.viewmodel.JournalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(
    entryId: Int,
    viewModel: JournalViewModel,
    onBackClick: () -> Unit
) {
    // the entry we load from the database (null until it is found)
    var entry by remember { mutableStateOf<JournalEntry?>(null) }

    // true while we are waiting for the database
    var isLoading by remember { mutableStateOf(true) }

    // true when the delete question is on the screen
    var showDeleteDialog by remember { mutableStateOf(false) }

    // This runs when the screen opens (and again if the id changes).
    // It asks the database for the entry with this id.
    LaunchedEffect(entryId) {
        entry = viewModel.getEntryById(entryId)
        isLoading = false
    }

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
                },
                actions = {
                    // the delete button only shows when an entry was found
                    if (entry != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete memory"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        val currentEntry = entry

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (currentEntry == null) {
                    // wrong id, no crash, only a message
                    Text(
                        text = "Sorry, this entry could not be found.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        text = currentEntry.title,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Place", style = MaterialTheme.typography.labelLarge)
                    Text(text = currentEntry.place, style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Date", style = MaterialTheme.typography.labelLarge)
                    Text(text = currentEntry.date, style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Weather", style = MaterialTheme.typography.labelLarge)
                    Text(text = currentEntry.weather, style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Note", style = MaterialTheme.typography.labelLarge)
                    if (currentEntry.note.isBlank()) {
                        Text(text = "No note written.", style = MaterialTheme.typography.bodyLarge)
                    } else {
                        Text(text = currentEntry.note, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }

    // the question that appears before deleting
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete memory?") },
            text = { Text("This memory will be removed from the phone and cannot be brought back.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteEntry(entryId)
                        onBackClick()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}