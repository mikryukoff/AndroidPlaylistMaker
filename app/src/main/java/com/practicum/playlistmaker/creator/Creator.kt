package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.network.ITunesApiFactory
import com.practicum.playlistmaker.data.network.TrackRepositoryImpl
import com.practicum.playlistmaker.domain.api.TrackSearchInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.db.DatabaseProvider
import com.practicum.playlistmaker.domain.impl.TrackSearchInteractorImpl

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TrackRepositoryImpl(
            networkClient = ITunesApiFactory.networkClient(),
            database = DatabaseProvider.instance,
        )
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}