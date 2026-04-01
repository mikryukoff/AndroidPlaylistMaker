package com.practicum.playlistmaker.ui.search

import com.practicum.playlistmaker.data.network.Track

sealed class SearchState {
    data object Initial : SearchState()
    data object Searching : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Fail(val error: String, val lastQuery: String) : SearchState()
}
