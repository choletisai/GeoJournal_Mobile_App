package com.example.geojournal.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geojournal.screens.AddEntryScreen
import com.example.geojournal.screens.EntryDetailScreen
import com.example.geojournal.screens.HomeScreen
import com.example.geojournal.screens.SettingsScreen
import com.example.geojournal.viewmodel.JournalViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // One ViewModel is made here and given to the screens that need it.
    // This way all screens use the same data.
    val journalViewModel: JournalViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        // Home screen
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = journalViewModel,
                onAddClick = { navController.navigate(Routes.ADD_ENTRY) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onEntryClick = { id -> navController.navigate(Routes.entryDetail(id)) }
            )
        }

        // Add entry screen
        composable(Routes.ADD_ENTRY) {
            AddEntryScreen(
                viewModel = journalViewModel,
                onBackClick = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        // Detail screen, it receives the id of the entry
        composable(
            route = Routes.ENTRY_DETAIL,
            arguments = listOf(
                navArgument("entryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1

            EntryDetailScreen(
                entryId = entryId,
                viewModel = journalViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Settings screen
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}