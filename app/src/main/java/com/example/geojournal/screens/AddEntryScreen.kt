package com.example.geojournal.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.geojournal.util.LocationHelper
import com.example.geojournal.util.PlaceHelper
import com.example.geojournal.viewmodel.JournalViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    viewModel: JournalViewModel,
    onBackClick: () -> Unit,
    onOpenCamera: () -> Unit,
    onSaved: () -> Unit
) {
    val context = LocalContext.current

    // rememberSaveable keeps the text when the user goes to the camera and comes back
    var title by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var cameraMessage by remember { mutableStateOf("") }
    var locationMessage by remember { mutableStateOf("") }
    var isFindingLocation by remember { mutableStateOf(false) }

    val maxTitle = 50
    val maxNote = 500

    // ---- finds the location and then the place name ----
    fun findLocation() {
        isFindingLocation = true
        locationMessage = ""

        LocationHelper.getCurrentLocation(context) { location ->
            if (location == null) {
                isFindingLocation = false
                locationMessage = "Could not find the location. Check that location is turned on."
            } else {
                PlaceHelper.getPlaceName(context, location.latitude, location.longitude) { placeName ->
                    viewModel.setDraftLocation(location.latitude, location.longitude, placeName)
                    isFindingLocation = false
                }
            }
        }
    }

    // ---- camera permission ----
    // The question is only shown when the user taps the photo button.
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraMessage = ""
            onOpenCamera()
        } else {
            cameraMessage = "Camera permission was refused. You can still save the memory without a photo."
        }
    }

    // ---- location permission ----
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        // it is enough if the user allowed one of the two (precise or approximate)
        if (results.values.any { it }) {
            findLocation()
        } else {
            locationMessage = "Location permission was refused. You can still save the memory without a place."
        }
    }

    // checks if we already have the permission. If yes, open the camera, if not, ask.
    fun openCameraWithPermission() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            onOpenCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun findLocationWithPermission() {
        val hasFine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFine || hasCoarse) {
            findLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

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

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- photo part ----------
            Text(text = "Photo", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))

            val photoPath = viewModel.draftPhotoPath

            if (photoPath != null) {
                // preview of the photo, with a button to take it again
                AsyncImage(
                    model = File(photoPath),
                    contentDescription = "Photo preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { openCameraWithPermission() }) {
                    Text("Retake photo")
                }
            } else {
                OutlinedButton(onClick = { openCameraWithPermission() }) {
                    Text("Take photo")
                }
            }

            if (cameraMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cameraMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- place part ----------
            Text(text = "Place", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))

            val place = viewModel.draftPlace

            if (isFindingLocation) {
                Text(text = "Finding your location...", style = MaterialTheme.typography.bodyLarge)
            } else if (place != null) {
                Text(text = place, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { findLocationWithPermission() }) {
                    Text("Update location")
                }
            } else {
                OutlinedButton(onClick = { findLocationWithPermission() }) {
                    Text("Add my location")
                }
            }

            if (locationMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = locationMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
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