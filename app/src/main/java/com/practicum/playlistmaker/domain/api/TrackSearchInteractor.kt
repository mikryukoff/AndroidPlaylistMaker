package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.data.network.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): List<Track>
}