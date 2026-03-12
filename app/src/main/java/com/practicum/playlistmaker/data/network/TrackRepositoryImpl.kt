package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl(retrofitNetworkClient: RetrofitNetworkClient) : TracksRepository {
    override suspend fun getAllTracks(): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) }
    }
}

val listTracks = listOf(
    Track(
        id = 1,
        trackName = "Владивосток 2000",
        artistName = "Мумий Троль",
        trackTime = "2:38",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 10,
        trackName = "Чёрный бумер",
        artistName = "Серега",
        trackTime = "4:01",
        image = "",
        favorite = false,
        playlistId = 0
    )
)
