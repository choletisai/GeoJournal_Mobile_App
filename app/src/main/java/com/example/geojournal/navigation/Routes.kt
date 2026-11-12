package com.example.geojournal.navigation

// All the screen names in one place, so I do not make spelling mistakes.
object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val ADD_ENTRY = "add_entry"
    const val SETTINGS = "settings"

    // The detail screen needs an id, so the route has a {entryId} part
    const val ENTRY_DETAIL = "entry_detail/{entryId}"

    // This function builds the real route, for example "entry_detail/2"
    fun entryDetail(id: Int): String {
        return "entry_detail/$id"
    }
}