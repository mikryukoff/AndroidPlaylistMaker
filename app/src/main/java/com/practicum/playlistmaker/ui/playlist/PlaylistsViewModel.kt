package com.practicum.playlistmaker.ui.playlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
) : ViewModel() {
    constructor() : this(
        playlistsRepository = object : PlaylistsRepository {
            override fun getPlaylist(playlistId: Long): Flow<Playlist?> = flowOf(null)
            override fun getAllPlaylists(): Flow<List<Playlist>> = flowOf(emptyList())
            override suspend fun addNewPlaylist(
                name: String,
                description: String,
                coverPath: String,
                createdAt: Long,
            ) = Unit
            override suspend fun deletePlaylistById(id: Long) = Unit
        },
        tracksRepository = object : TracksRepository {
            override suspend fun searchTracks(expression: String): List<Track> = emptyList()
            override fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flowOf(null)
            override fun getFavoriteTracks(): Flow<List<Track>> = flowOf(emptyList())
            override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) = Unit
            override suspend fun deleteTracksByPlaylistId(playlistId: Long) = Unit
            override suspend fun deleteTrackFromPlaylist(track: Track) = Unit
            override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) = Unit
        },
    )


    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun createNewPlayList(
        namePlaylist: String,
        description: String,
        coverPath: String,
        createdAt: Long,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(
                name = namePlaylist,
                description = description,
                coverPath = coverPath,
                createdAt = createdAt,
            )
        }
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track = track).firstOrNull()
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(
                        playlistsRepository = Creator.getPlaylistsRepository(context),
                        tracksRepository = Creator.getTracksRepository(context),
                    ) as T
                }
            }
    }
}
