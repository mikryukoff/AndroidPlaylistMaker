package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.db.AppDatabase
import com.practicum.playlistmaker.data.db.entity.PlaylistEntity
import com.practicum.playlistmaker.data.db.mapper.toDomain
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase,
) : PlaylistsRepository {
    private val playlistDao = database.playlistDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistDao.getPlaylistById(playlistId).map { it?.toDomain() }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
            ),
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deletePlaylistById(playlistId = id)
    }
}
