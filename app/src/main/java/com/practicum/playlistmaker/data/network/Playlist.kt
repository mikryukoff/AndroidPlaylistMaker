package com.practicum.playlistmaker.data.network

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    var tracks: List<Track>,
    val coverPath: String = "",
    val createdAt: Long = 0L,
)