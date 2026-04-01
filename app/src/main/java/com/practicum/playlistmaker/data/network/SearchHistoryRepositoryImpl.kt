package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.db.DatabaseMock
import com.practicum.playlistmaker.domain.db.DatabaseProvider

class SearchHistoryRepositoryImpl(
    private val database: DatabaseMock = DatabaseProvider.instance,
) : SearchHistoryRepository {

    override suspend fun getHistoryRequests(): List<String> {
        return database.getHistory()
    }

    override fun addToHistory(word: Word) {
        database.addToHistory(word = word.word)
    }
}
