package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.preferences.SearchHistoryPreferences
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences,
) : SearchHistoryRepository {

    override suspend fun getHistoryRequests(): List<String> {
        return preferences.getEntries()
    }

    override fun addToHistory(word: Word) {
        preferences.addEntry(word = word.word)
    }
}
