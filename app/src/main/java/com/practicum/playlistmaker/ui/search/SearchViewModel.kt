package com.practicum.playlistmaker.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.data.network.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.network.Word
import com.practicum.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {
    private val searchHistoryRepository = SearchHistoryRepositoryImpl()
    private val _searchQuery = MutableStateFlow("")
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val searchGeneration = AtomicInteger(0)

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    val trimmed = query.trim()
                    if (trimmed.isNotEmpty()) {
                        performSearch(trimmed)
                    }
                }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        searchGeneration.incrementAndGet()
        _searchQuery.value = ""
        _searchScreenState.value = SearchState.Initial
    }

    fun retryLastSearch() {
        val state = _searchScreenState.value
        if (state is SearchState.Fail) {
            performSearch(state.lastQuery)
        }
    }

    private fun performSearch(trimmedQuery: String) {
        if (trimmedQuery.isEmpty()) return
        val generation = searchGeneration.incrementAndGet()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(Word(word = trimmedQuery))
                val list = tracksRepository.searchTracks(trimmedQuery)
                if (generation != searchGeneration.get()) return@launch
                _searchScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: IOException) {
                if (generation != searchGeneration.get()) return@launch
                val message = e.message?.trim().orEmpty().ifEmpty { "Network error" }
                _searchScreenState.update {
                    SearchState.Fail(error = message, lastQuery = trimmedQuery)
                }
            }
        }
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistoryRequests()
}
