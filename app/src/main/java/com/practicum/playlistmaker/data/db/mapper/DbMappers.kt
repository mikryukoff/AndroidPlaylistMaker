package com.practicum.playlistmaker.data.db.mapper

import com.practicum.playlistmaker.data.db.entity.PlaylistEntity
import com.practicum.playlistmaker.data.db.entity.TrackEntity
import com.practicum.playlistmaker.data.db.model.PlaylistWithTracks
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.data.network.Track

fun Track.toEntity(): TrackEntity = TrackEntity(
    id = id,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    image = image,
    favorite = favorite,
)

fun TrackEntity.toDomain(): Track = Track(
    id = id,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    image = image,
    favorite = favorite,
    playlistId = 0L,
)

fun PlaylistEntity.toDomain(tracks: List<Track>): Playlist = Playlist(
    id = id,
    name = name,
    description = description,
    tracks = tracks,
)

fun PlaylistWithTracks.toDomain(): Playlist = playlist.toDomain(
    tracks = tracks.map { track ->
        track.toDomain().copy(playlistId = playlist.id)
    },
)
