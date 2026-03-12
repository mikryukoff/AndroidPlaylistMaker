package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.db.DatabaseMock
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(private val scope: CoroutineScope): SearchHistoryRepository {
    private val database = DatabaseMock(scope = scope)

    override suspend fun getHistoryRequests(): List<String> {
        return database.getHistoryRequests()
    }

    override fun addToHistory(word: Word) {
        database.addToHistory(word = word.word)
    }
}
