package com.practicum.playlistmaker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practicum.playlistmaker.ui.main.MenuScreen
import com.practicum.playlistmaker.ui.search.SearchScreen
import com.practicum.playlistmaker.ui.search.SearchViewModel
import com.practicum.playlistmaker.ui.settings.SettingsScreen

enum class Screen(val route: String) {
    MAIN_MENU("main_menu"),
    SEARCH("search"),
    SETTINGS("settings")
}

@Composable
fun PlaylistHost(
    startDestination: String = "main_menu",
    navController: NavHostController = rememberNavController(),
    searchViewModel: SearchViewModel,
) {
    NavHost(navController, startDestination) {
        composable(route = Screen.MAIN_MENU.route) {
            MenuScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) }
            )
        }
        composable(route = Screen.SEARCH.route) {
            SearchScreen(
                modifier = Modifier,
                onClick = { },
                onBackClick = { navController.navigate(Screen.MAIN_MENU.route) },
                searchViewModel = searchViewModel
            )
        }
        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.navigate(Screen.MAIN_MENU.route) })
        }
    }
}
