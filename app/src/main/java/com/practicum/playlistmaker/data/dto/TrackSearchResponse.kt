package com.practicum.playlistmaker.data.dto

class TracksSearchResponse(
    val resultCount: Int? = null,
    val results: List<TrackDto>? = null,
) : BaseResponse()
