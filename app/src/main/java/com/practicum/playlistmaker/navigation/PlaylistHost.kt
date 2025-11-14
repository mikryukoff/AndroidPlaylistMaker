package com.practicum.playlistmaker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practicum.playlistmaker.screens.MenuScreen
import com.practicum.playlistmaker.screens.SearchScreen
import com.practicum.playlistmaker.screens.SettingsScreen

enum class Screen(val route: String) {
    MAIN_MENU("main_menu"),
    SEARCH("search"),
    SETTINGS("settings")
}

@Composable
fun PlaylistHost(
    startDestination: String = "main_menu",
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination) {
        composable(route = Screen.MAIN_MENU.route) {
            MenuScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) }
            )
        }
        composable(route = Screen.SEARCH.route) {
            SearchScreen(onBackClick = { navController.navigate(Screen.MAIN_MENU.route) })
        }
        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.navigate(Screen.MAIN_MENU.route) })
        }
    }
}
