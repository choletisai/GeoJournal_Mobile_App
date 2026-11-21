package com.example.geojournal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.geojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow

// DAO means Data Access Object.
// It is a list of the things the app can ask the database to do.
@Dao
interface JournalDao {

    // save one new entry
    @Insert
    suspend fun insert(entry: JournalEntry)

    // get all entries, newest first.
    // New entries get a bigger id, so sorting by id gives the newest on top.
    // Flow means the list updates by itself when the data changes.
    @Query("SELECT * FROM journal_entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>

    // get one entry. It can be null if the id does not exist.
    @Query("SELECT * FROM journal_entries WHERE id = :entryId")
    suspend fun getEntryById(entryId: Int): JournalEntry?

    // delete one entry by its id
    @Query("DELETE FROM journal_entries WHERE id = :entryId")
    suspend fun deleteById(entryId: Int)
}