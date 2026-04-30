package com.practicum.playlistmaker.data.dto

import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.data.utils.toHighResolutionArtwork

internal fun TrackDto.toDomainTrack(): Track? {
    if (kind != null && kind != "song") return null
    val name = trackName?.trim().orEmpty()
    if (name.isEmpty()) return null
    val id = trackId ?: return null
    return Track(
        id = id,
        trackName = name,
        artistName = artistName?.trim().orEmpty().ifEmpty { "—" },
        trackTime = formatTrackDurationMillis(trackTimeMillis),
        image = toHighResolutionArtwork(artworkUrl100),
        favorite = false,
        playlistId = 0L,
    )
}

internal fun TracksSearchResponse.toDomainTracks(): List<Track> {
    return results.orEmpty().mapNotNull { it.toDomainTrack() }
}

private fun formatTrackDurationMillis(millis: Long?): String {
    if (millis == null || millis <= 0L) return "0:00"
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
