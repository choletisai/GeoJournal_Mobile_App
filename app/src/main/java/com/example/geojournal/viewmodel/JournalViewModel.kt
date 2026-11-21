package com.example.geojournal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.geojournal.data.AppDatabase
import com.example.geojournal.data.JournalRepository
import com.example.geojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// The ViewModel holds the data for the screens.
// It stays alive when the phone is rotated, so the data is not lost.
class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JournalRepository

    // the list of all entries, the Home screen reads this
    val allEntries: Flow<List<JournalEntry>>

    init {
        val dao = AppDatabase.getDatabase(application).journalDao()
        repository = JournalRepository(dao)
        allEntries = repository.allEntries
    }

    // saves a new entry with today's date
    fun addEntry(title: String, note: String) {
        // viewModelScope.launch runs the work in the background
        viewModelScope.launch {
            val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            val entry = JournalEntry(
                title = title,
                note = note,
                date = today
            )
            repository.addEntry(entry)
        }
    }

    // finds one entry for the Detail screen
    suspend fun getEntryById(id: Int): JournalEntry? {
        return repository.getEntryById(id)
    }

    fun deleteEntry(id: Int) {
        viewModelScope.launch {
            repository.deleteEntry(id)
        }
    }
}