package com.example.geojournal.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.geojournal.viewmodel.JournalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    viewModel: JournalViewModel,
    onBackClick: () -> Unit,
    onSaved: () -> Unit
) {
    val context = LocalContext.current

    // what the user types
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    // becomes true when the user presses Save with an empty title
    var titleError by remember { mutableStateOf(false) }

    // maximum lengths, so the app does not get very large text
    val maxTitle = 50
    val maxNote = 500

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Memory") },
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

            OutlinedTextField(
                value = title,
                onValueChange = {
                    // only accept the text if it is not too long
                    if (it.length <= maxTitle) {
                        title = it
                        titleError = false
                    }
                },
                label = { Text("Title") },
                singleLine = true,
                isError = titleError,
                supportingText = {
                    if (titleError) {
                        Text("Please enter a title")
                    } else {
                        Text("${title.length}/$maxTitle")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = note,
                onValueChange = {
                    if (it.length <= maxNote) {
                        note = it
                    }
                },
                label = { Text("Write your note") },
                minLines = 4,
                supportingText = { Text("${note.length}/$maxNote") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        // title is empty, show the error and do not save
                        titleError = true
                    } else {
                        viewModel.addEntry(title.trim(), note.trim())
                        Toast.makeText(context, "Memory saved", Toast.LENGTH_SHORT).show()
                        onSaved()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}