package com.practicum.playlistmaker.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.data.network.PlaylistsRepositoryImpl
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PlaylistViewModel(
    playlistsRepository: PlaylistsRepository,
    playlistId: Long,
) : ViewModel() {

    val playlist: StateFlow<Playlist?> = playlistsRepository
        .getPlaylist(playlistId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    companion object {
        fun factory(playlistId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repo: PlaylistsRepository = PlaylistsRepositoryImpl()
                    return PlaylistViewModel(repo, playlistId) as T
                }
            }
    }
}
