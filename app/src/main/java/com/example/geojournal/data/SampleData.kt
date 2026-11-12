package com.example.geojournal.data

import com.example.geojournal.model.JournalEntry

// Made-up data just for Sprint 1, so the screens have something to show.
// No real personal information is used here.
object SampleData {

    val entries = listOf(
        JournalEntry(
            id = 1,
            title = "Sunset at the beach",
            note = "Walked along the beach after class. The sky turned orange and it was very quiet.",
            place = "Redcar Beach",
            date = "05 Oct 2026",
            weather = "Clear sky, 14°C"
        ),
        JournalEntry(
            id = 2,
            title = "Hike with friends",
            note = "Climbed the hill early in the morning. A bit tiring but the view was worth it.",
            place = "Roseberry Topping",
            date = "18 Oct 2026",
            weather = "Cloudy, 10°C"
        ),
        JournalEntry(
            id = 3,
            title = "Coffee and study",
            note = "Found a nice cafe to study for my exams. Good coffee and fast wifi.",
            place = "Middlesbrough Centre",
            date = "29 Oct 2026",
            weather = "Light rain, 9°C"
        ),
        JournalEntry(
            id = 4,
            title = "Walk by the river",
            note = "Took a short break and walked near the river. Needed some fresh air.",
            place = "Tees Riverside",
            date = "03 Nov 2026",
            weather = "Windy, 8°C"
        )
    )
}