package com.practicum.playlistmaker.creator

import android.content.Context
import com.practicum.playlistmaker.data.network.ITunesApiFactory
import com.practicum.playlistmaker.data.network.PlaylistsRepositoryImpl
import com.practicum.playlistmaker.data.network.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.network.TrackRepositoryImpl
import com.practicum.playlistmaker.data.preferences.SearchHistoryPreferences
import com.practicum.playlistmaker.data.storage.StorageProvider
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.api.TrackSearchInteractor
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.impl.TrackSearchInteractorImpl

object Creator {
    fun getTracksRepository(context: Context): TracksRepository {
        val database = StorageProvider.provideDatabase(context)
        return TrackRepositoryImpl(
            networkClient = ITunesApiFactory.networkClient(),
            trackDao = database.trackDao(),
            playlistTrackDao = database.playlistTrackDao(),
        )
    }

    fun getPlaylistsRepository(context: Context): PlaylistsRepository {
        return PlaylistsRepositoryImpl(
            database = StorageProvider.provideDatabase(context),
        )
    }

    fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        val preferences = SearchHistoryPreferences(
            dataStore = StorageProvider.provideSearchHistoryDataStore(context),
        )
        return SearchHistoryRepositoryImpl(preferences = preferences)
    }

    fun provideTrackSearchInteractor(context: Context): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository(context))
    }
}