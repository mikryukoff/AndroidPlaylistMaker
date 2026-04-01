package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.network.Track

interface TrackSearchInteractor {
    suspend fun searchTracks(expression: String): List<Track>
}