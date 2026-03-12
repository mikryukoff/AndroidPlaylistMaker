package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.network.Track

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}
