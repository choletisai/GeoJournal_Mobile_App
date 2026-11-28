package com.example.geojournal.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.geojournal.data.AppDatabase
import com.example.geojournal.data.JournalRepository
import com.example.geojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JournalRepository
    val allEntries: Flow<List<JournalEntry>>

    // ---- the "draft" is the memory that is being written right now ----
    // They are kept here (and not in the screen) because the camera is a different screen.
    var draftPhotoPath by mutableStateOf<String?>(null)
        private set
    var draftLatitude by mutableStateOf<Double?>(null)
        private set
    var draftLongitude by mutableStateOf<Double?>(null)
        private set
    var draftPlace by mutableStateOf<String?>(null)
        private set

    init {
        val dao = AppDatabase.getDatabase(application).journalDao()
        repository = JournalRepository(dao)
        allEntries = repository.allEntries
    }

    // called by the camera screen after a photo is taken
    fun setDraftPhoto(path: String) {
        // if there was an older photo (retake), delete its file
        draftPhotoPath?.let { File(it).delete() }
        draftPhotoPath = path
    }

    // called by the Add Entry screen when the place is found
    fun setDraftLocation(latitude: Double, longitude: Double, place: String) {
        draftLatitude = latitude
        draftLongitude = longitude
        draftPlace = place
    }

    // empties the draft. If the memory was not saved, the photo file is deleted too.
    fun clearDraft(deletePhoto: Boolean) {
        if (deletePhoto) {
            draftPhotoPath?.let { File(it).delete() }
        }
        draftPhotoPath = null
        draftLatitude = null
        draftLongitude = null
        draftPlace = null
    }

    fun addEntry(title: String, note: String) {
        // copy the draft values first, because clearDraft empties them
        val photo = draftPhotoPath
        val lat = draftLatitude
        val lon = draftLongitude
        val placeName = draftPlace

        clearDraft(deletePhoto = false)

        viewModelScope.launch {
            val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            val entry = JournalEntry(
                title = title,
                note = note,
                place = placeName ?: "Not added yet",
                date = today,
                photoPath = photo,
                latitude = lat,
                longitude = lon
            )
            repository.addEntry(entry)
        }
    }

    suspend fun getEntryById(id: Int): JournalEntry? {
        return repository.getEntryById(id)
    }

    // When an entry is deleted, its photo file is deleted too,
    // so no private picture is left on the phone.
    fun deleteEntry(entry: JournalEntry) {
        entry.photoPath?.let { File(it).delete() }

        viewModelScope.launch {
            repository.deleteEntry(entry.id)
        }
    }
}