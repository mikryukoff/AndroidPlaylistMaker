package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.network.Word

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): List<String>

    fun addToHistory(word: Word)
}