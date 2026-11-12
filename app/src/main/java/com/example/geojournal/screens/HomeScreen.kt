package com.example.geojournal.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.geojournal.model.JournalEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEntryClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Memories") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add entry"
                )
            }
        }
    ) { innerPadding ->

        // LazyColumn is the list. It only draws the items that are visible.
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(SampleData.entries) { entry ->
                EntryCard(
                    entry = entry,
                    onClick = { onEntryClick(entry.id) }
                )
            }
        }
    }
}

@Composable
fun EntryCard(entry: JournalEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = entry.place + " - " + entry.date,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}