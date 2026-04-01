package com.practicum.playlistmaker.ui.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.ui.search.TrackListItem
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme
import com.practicum.playlistmaker.ui.utils.TopAppButtonBar

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel,
    onTrackClick: (Track) -> Unit,
    navigateBack: () -> Unit,
) {
    val playlist by playlistViewModel.playlist.collectAsState()
    PlaylistScreenContent(
        modifier = modifier,
        playlist = playlist,
        onTrackClick = onTrackClick,
        navigateBack = navigateBack,
    )
}

@Composable
internal fun PlaylistScreenContent(
    modifier: Modifier = Modifier,
    playlist: Playlist?,
    onTrackClick: (Track) -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val p = playlist

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppButtonBar(
                context = context,
                text = p?.name ?: stringResource(R.string.playlists),
                onClick = navigateBack,
            )
        },
    ) { paddingValues ->
        when {
            p == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.playlist_not_found),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            p.tracks.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.playlist_empty),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    p.description.takeIf { it.isNotBlank() }?.let { desc ->
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        val tracks = p.tracks
                        items(tracks.size) { index ->
                            TrackListItem(
                                track = tracks[index],
                                onClick = { onTrackClick(tracks[index]) },
                            )
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

private val previewPlaylistWithTracks = Playlist(
    id = 1L,
    name = "Мой плейлист",
    description = "Описание для превью — несколько строк, чтобы проверить отступы.",
    tracks = listOf(
        Track(
            id = 101L,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "3:34",
            image = "",
            favorite = false,
            playlistId = 1L,
        ),
        Track(
            id = 102L,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTime = "4:45",
            image = "",
            favorite = true,
            playlistId = 1L,
        ),
    ),
)

@Preview(showBackground = true, name = "С треками")
@Composable
private fun PlaylistScreenPreviewWithTracks() {
    PlaylistMakerTheme(dynamicColor = false) {
        PlaylistScreenContent(
            playlist = previewPlaylistWithTracks,
            onTrackClick = {},
            navigateBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Пустой плейлист")
@Composable
private fun PlaylistScreenPreviewEmpty() {
    PlaylistMakerTheme(dynamicColor = false) {
        PlaylistScreenContent(
            playlist = Playlist(
                id = 2L,
                name = "Пустой",
                description = "",
                tracks = emptyList(),
            ),
            onTrackClick = {},
            navigateBack = {},
        )
    }
}

@Preview(showBackground = true, name = "Не найден")
@Composable
private fun PlaylistScreenPreviewNotFound() {
    PlaylistMakerTheme(dynamicColor = false) {
        PlaylistScreenContent(
            playlist = null,
            onTrackClick = {},
            navigateBack = {},
        )
    }
}
