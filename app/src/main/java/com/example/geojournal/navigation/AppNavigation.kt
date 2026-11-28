package com.example.geojournal.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geojournal.screens.AddEntryScreen
import com.example.geojournal.screens.CameraScreen
import com.example.geojournal.screens.EntryDetailScreen
import com.example.geojournal.screens.HomeScreen
import com.example.geojournal.screens.SettingsScreen
import com.example.geojournal.viewmodel.JournalViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val journalViewModel: JournalViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = journalViewModel,
                onAddClick = {
                    // start with a clean draft each time a new memory is begun
                    journalViewModel.clearDraft(deletePhoto = true)
                    navController.navigate(Routes.ADD_ENTRY)
                },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onEntryClick = { id -> navController.navigate(Routes.entryDetail(id)) }
            )
        }

        composable(Routes.ADD_ENTRY) {
            AddEntryScreen(
                viewModel = journalViewModel,
                onBackClick = { navController.popBackStack() },
                onOpenCamera = { navController.navigate(Routes.CAMERA) },
                onSaved = { navController.popBackStack() }
            )
        }

        // The camera is its own screen. After the photo is taken we go back to Add Entry.
        composable(Routes.CAMERA) {
            CameraScreen(
                viewModel = journalViewModel,
                onBackClick = { navController.popBackStack() },
                onPhotoTaken = { navController.popBackStack() }
            )
        }

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

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}