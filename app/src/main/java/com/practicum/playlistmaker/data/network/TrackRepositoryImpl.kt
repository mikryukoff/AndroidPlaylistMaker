package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.dto.TracksSearchRequest
import com.practicum.playlistmaker.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.data.dto.toDomainTracks
import com.practicum.playlistmaker.data.db.dao.PlaylistTrackDao
import com.practicum.playlistmaker.data.db.dao.TrackDao
import com.practicum.playlistmaker.data.db.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.data.db.mapper.toDomain
import com.practicum.playlistmaker.data.db.mapper.toEntity
import com.practicum.playlistmaker.domain.NetworkClient
import com.practicum.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackDao: TrackDao,
    private val playlistTrackDao: PlaylistTrackDao,
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
        return trackDao
            .getTrackByNameAndArtist(track.trackName, track.artistName)
            .map { it?.toDomain() }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        trackDao.upsertTrack(track.toEntity())
        playlistTrackDao.insertCrossRef(
            PlaylistTrackCrossRef(
                playlistId = playlistId,
                trackId = track.id,
            ),
        )
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        if (track.playlistId > 0L) {
            playlistTrackDao.deleteCrossRef(playlistId = track.playlistId, trackId = track.id)
            deleteTrackIfUnused(track.id)
        } else {
            playlistTrackDao.deleteByTrackId(track.id)
            deleteTrackIfUnused(track.id)
        }
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val stored = trackDao.getTrackById(track.id)
        if (stored == null) {
            trackDao.upsertTrack(track.copy(favorite = isFavorite).toEntity())
        } else {
            trackDao.updateFavorite(trackId = track.id, isFavorite = isFavorite)
        }
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        val trackIds = playlistTrackDao.getTrackIdsByPlaylistId(playlistId)
        playlistTrackDao.deleteByPlaylistId(playlistId)
        trackIds.forEach { trackId ->
            deleteTrackIfUnused(trackId)
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavoriteTracks().map { tracks -> tracks.map { it.toDomain() } }
    }

    private suspend fun deleteTrackIfUnused(trackId: Long) {
        val track = trackDao.getTrackById(trackId) ?: return
        if (playlistTrackDao.getTrackUsageCount(trackId) == 0 && !track.favorite) {
            trackDao.deleteTrackById(trackId)
        }
    }
}
