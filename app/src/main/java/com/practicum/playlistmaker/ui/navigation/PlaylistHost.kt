package com.practicum.playlistmaker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.ui.favorites.FavoritesScreen
import com.practicum.playlistmaker.ui.main.MenuScreen
import com.practicum.playlistmaker.ui.player.PlayerScreen
import com.practicum.playlistmaker.ui.playlist.CreatePlaylistScreen
import com.practicum.playlistmaker.ui.playlist.PlaylistScreen
import com.practicum.playlistmaker.ui.playlist.PlaylistViewModel
import com.practicum.playlistmaker.ui.playlist.PlaylistsScreen
import com.practicum.playlistmaker.ui.playlist.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.SearchScreen
import com.practicum.playlistmaker.ui.search.SearchViewModel
import com.practicum.playlistmaker.ui.settings.SettingsScreen

enum class Screen(val route: String) {
    MAIN_MENU("main_menu"),
    SEARCH("search"),
    SETTINGS("settings"),
    PLAYLISTS("playlists"),
    FAVORITES("favorites"),
    CREATE_PLAYLIST("create_playlist"),
    PLAYLIST_SCREEN("playlist_screen"),
    PLAYER("player"),
}

@Composable
fun PlaylistHost(
    startDestination: String = Screen.MAIN_MENU.route,
    navController: NavHostController = rememberNavController(),
    searchViewModel: SearchViewModel,
    playlistsViewModel: PlaylistsViewModel,
) {
    val modifier = Modifier
    val context = LocalContext.current

    fun openPlayer(track: Track) {
        PlayerNavigationArgs.pendingTrack = track
        navController.navigate(Screen.PLAYER.route)
    }

    NavHost(navController, startDestination) {
        composable(route = Screen.MAIN_MENU.route) {
            MenuScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) },
                onPlaylistsClick = { navController.navigate(Screen.PLAYLISTS.route) },
                onFavoritesClick = { navController.navigate(Screen.FAVORITES.route) },
            )
        }
        composable(route = Screen.SEARCH.route) {
            SearchScreen(
                modifier = modifier,
                searchViewModel = searchViewModel,
                onTrackClick = { track -> openPlayer(track) },
                onBackClick = {
                    searchViewModel.clearSearch()
                    navController.popBackStack()
                },
            )
        }
        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = { navController.navigate(Screen.CREATE_PLAYLIST.route) },
                navigateToPlaylist = { playlistId ->
                    navController.navigate("${Screen.PLAYLIST_SCREEN.route}/$playlistId")
                },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = Screen.FAVORITES.route) {
            FavoritesScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                onTrackClick = { track -> openPlayer(track) },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = Screen.CREATE_PLAYLIST.route) {
            CreatePlaylistScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = "${Screen.PLAYLIST_SCREEN.route}/{playlistId}",
            arguments = listOf(
                navArgument("playlistId") {
                    type = NavType.LongType
                },
            ),
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            val playlistViewModel: PlaylistViewModel = viewModel(
                key = "playlist_$playlistId",
                factory = PlaylistViewModel.factory(playlistId, context),
            )
            PlaylistScreen(
                modifier = modifier,
                playlistViewModel = playlistViewModel,
                playlistsViewModel = playlistsViewModel,
                onTrackClick = { track -> openPlayer(track) },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = Screen.PLAYER.route) {
            PlayerScreen(
                modifier = modifier,
                playlistsViewModel = playlistsViewModel,
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}
