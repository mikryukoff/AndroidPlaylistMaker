package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.network.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}