package com.example.geojournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geojournal.screens.AddEntryScreen
import com.example.geojournal.screens.EntryDetailScreen
import com.example.geojournal.screens.HomeScreen
import com.example.geojournal.screens.SettingsScreen
import com.example.geojournal.screens.SplashScreen

@Composable
fun AppNavigation() {

    // the navController is what moves us between screens
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // Splash screen, goes to Home when it finishes
        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.HOME) {
                        // remove splash from the back stack so back button does not return to it
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // Home screen
        composable(Routes.HOME) {
            HomeScreen(
                onAddClick = { navController.navigate(Routes.ADD_ENTRY) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onEntryClick = { id -> navController.navigate(Routes.entryDetail(id)) }
            )
        }

        // Add entry screen
        composable(Routes.ADD_ENTRY) {
            AddEntryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Detail screen, it receives the id of the entry
        composable(
            route = Routes.ENTRY_DETAIL,
            arguments = listOf(
                navArgument("entryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // get the id that was sent from the Home screen (-1 if something goes wrong)
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1

            EntryDetailScreen(
                entryId = entryId,
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