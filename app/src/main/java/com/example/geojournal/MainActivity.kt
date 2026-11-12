package com.example.geojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.geojournal.navigation.AppNavigation
import com.example.geojournal.ui.theme.GeoJournalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GeoJournalTheme {
                // the whole app is inside AppNavigation
                AppNavigation()
            }
        }
    }
}