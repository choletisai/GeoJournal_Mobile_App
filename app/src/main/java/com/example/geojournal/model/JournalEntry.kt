package com.example.geojournal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// This class is now also a table in the database.
// One JournalEntry is one row in the table.
@Entity(tableName = "journal_entries")
data class JournalEntry(

    // the id is made by the database itself, so 0 is only a starting value
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val note: String,

    // place and weather come in Sprint 3 and 4, so for now they have a default text
    val place: String = "Not added yet",
    val date: String,
    val weather: String = "Not added yet"
)