package com.practicum.playlistmaker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.practicum.playlistmaker.ui.navigation.PlaylistHost
import com.practicum.playlistmaker.ui.search.SearchViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel>{
        SearchViewModel.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistHost(searchViewModel = searchViewModel)
        }
    }
}