package com.example.geojournal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val note: String,
    val place: String = "Not added yet",
    val date: String,
    val weather: String = "Not added yet",

    // new in Sprint 3. They can be null because a memory may have no photo or location.
    val photoPath: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)