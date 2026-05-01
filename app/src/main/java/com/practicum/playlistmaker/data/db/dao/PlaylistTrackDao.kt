package com.practicum.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.data.db.entity.PlaylistTrackCrossRef

@Dao
interface PlaylistTrackDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: PlaylistTrackCrossRef)

    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId")
    suspend fun deleteByPlaylistId(playlistId: Long)

    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun deleteCrossRef(playlistId: Long, trackId: Long)

    @Query("DELETE FROM playlist_track_cross_ref WHERE trackId = :trackId")
    suspend fun deleteByTrackId(trackId: Long)

    @Query("SELECT COUNT(*) FROM playlist_track_cross_ref WHERE trackId = :trackId")
    suspend fun getTrackUsageCount(trackId: Long): Int

    @Query("SELECT trackId FROM playlist_track_cross_ref WHERE playlistId = :playlistId")
    suspend fun getTrackIdsByPlaylistId(playlistId: Long): List<Long>
}
