package com.practicum.playlistmaker.domain.impl

import com.practicum.playlistmaker.domain.api.TrackSearchInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.data.network.Track


class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor {

    override suspend fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }
}