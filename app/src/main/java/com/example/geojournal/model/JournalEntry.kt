package com.example.geojournal.model

// This class describes one diary entry.
// Later (Sprint 2) this will also be used for the database.
data class JournalEntry(
    val id: Int,
    val title: String,
    val note: String,
    val place: String,
    val date: String,
    val weather: String
)