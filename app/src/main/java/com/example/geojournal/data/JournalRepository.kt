package com.example.geojournal.data

import com.example.geojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow

// The repository stands between the ViewModel and the database.
// The ViewModel does not need to know how the data is stored.
class JournalRepository(private val dao: JournalDao) {

    val allEntries: Flow<List<JournalEntry>> = dao.getAllEntries()

    suspend fun addEntry(entry: JournalEntry) {
        dao.insert(entry)
    }

    suspend fun getEntryById(id: Int): JournalEntry? {
        return dao.getEntryById(id)
    }

    suspend fun deleteEntry(id: Int) {
        dao.deleteById(id)
    }
}