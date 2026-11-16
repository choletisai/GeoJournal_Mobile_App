package com.example.geojournal.navigation

object Routes {
    const val HOME = "home"
    const val ADD_ENTRY = "add_entry"
    const val SETTINGS = "settings"


    const val ENTRY_DETAIL = "entry_detail/{entryId}"


    fun entryDetail(id: Int): String {
        return "entry_detail/$id"
    }
}