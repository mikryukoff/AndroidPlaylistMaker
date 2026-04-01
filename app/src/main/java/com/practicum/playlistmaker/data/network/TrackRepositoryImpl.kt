package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.dto.TracksSearchRequest
import com.practicum.playlistmaker.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.data.dto.toDomainTracks
import com.practicum.playlistmaker.domain.NetworkClient
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.db.DatabaseMock
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: DatabaseMock,
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val q = expression.trim()
        if (q.isEmpty()) return emptyList()
        val response = networkClient.doRequest(TracksSearchRequest(q))
        if (response is TracksSearchResponse) {
            return response.toDomainTracks()
        }
        if (response.resultCode != 0) {
            throw IOException(
                response.errorMessage.ifEmpty { "Request failed with code ${response.resultCode}" },
            )
        }
        return emptyList()
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }
}
