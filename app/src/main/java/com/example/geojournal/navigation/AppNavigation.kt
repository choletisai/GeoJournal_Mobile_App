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

@Composable
fun AppNavigation() {


    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        // Home screen
        composable(Routes.HOME) {
            HomeScreen(
                onAddClick = { navController.navigate(Routes.ADD_ENTRY) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onEntryClick = { id -> navController.navigate(Routes.entryDetail(id)) }
            )
        }

        composable(Routes.ADD_ENTRY) {
            AddEntryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }


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