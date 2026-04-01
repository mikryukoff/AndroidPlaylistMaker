package com.practicum.playlistmaker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.practicum.playlistmaker.ui.navigation.PlaylistHost
import com.practicum.playlistmaker.ui.playlist.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.SearchViewModel

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory()
    }
    private val playlistsViewModel by viewModels<PlaylistsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistHost(
                searchViewModel = searchViewModel,
                playlistsViewModel = playlistsViewModel,
            )
        }
    }
}