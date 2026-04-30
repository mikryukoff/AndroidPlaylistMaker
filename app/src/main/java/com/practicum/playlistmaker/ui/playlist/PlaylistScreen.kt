package com.practicum.playlistmaker.ui.playlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.ui.search.TrackListItem
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme
import com.practicum.playlistmaker.ui.utils.TopAppButtonBar
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel,
    playlistsViewModel: PlaylistsViewModel,
    onTrackClick: (Track) -> Unit,
    navigateBack: () -> Unit,
) {
    val playlist by playlistViewModel.playlist.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    PlaylistScreenContent(
        modifier = modifier,
        playlist = playlist,
        onTrackClick = onTrackClick,
        onTrackLongClick = { track ->
            coroutineScope.launch {
                playlistsViewModel.deleteTrackFromPlaylist(track)
                Toast.makeText(
                    context,
                    context.getString(R.string.removed_from_playlist_message, playlist?.name.orEmpty()),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        },
        navigateBack = navigateBack,
    )
}

@Composable
internal fun PlaylistScreenContent(
    modifier: Modifier = Modifier,
    playlist: Playlist?,
    onTrackClick: (Track) -> Unit,
    onTrackLongClick: (Track) -> Unit,
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

            else -> {
                val tracksCount = p.tracks.size
                val totalMinutes = calculatePlaylistMinutes(p.tracks)
                val year = formatPlaylistYear(p.createdAt)
                val minutesLabel = pluralStringResource(
                    id = R.plurals.minutes_count,
                    count = totalMinutes,
                    totalMinutes,
                )
                val tracksLabel = pluralStringResource(
                    id = R.plurals.tracks_count,
                    count = tracksCount,
                    tracksCount,
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val coverModel = p.coverPath.takeIf { it.isNotBlank() }?.let(::File)
                        if (coverModel == null) {
                            Image(
                                painter = painterResource(R.drawable.add_playlist_photo),
                                contentDescription = stringResource(R.string.playlist_name),
                            )
                        } else {
                            AsyncImage(
                                model = coverModel,
                                contentDescription = stringResource(R.string.playlist_name),
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                    Text(
                        text = p.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                    if (year.isNotBlank()) {
                        Text(
                            text = year,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        )
                    }
                    Text(
                        text = "$minutesLabel · $tracksLabel",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                    p.description.takeIf { it.isNotBlank() }?.let { description ->
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                    if (p.tracks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(R.string.playlist_empty),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            val tracks = p.tracks
                            items(tracks.size) { index ->
                                TrackListItem(
                                    track = tracks[index],
                                    onClick = { onTrackClick(tracks[index]) },
                                    onLongClick = { onTrackLongClick(tracks[index]) },
                                )
                                HorizontalDivider(thickness = 0.5.dp)
                            }
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
    createdAt = System.currentTimeMillis(),
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
            onTrackLongClick = {},
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
                createdAt = System.currentTimeMillis(),
                tracks = emptyList(),
            ),
            onTrackClick = {},
            onTrackLongClick = {},
            navigateBack = {},
        )
    }
}

private fun formatPlaylistYear(createdAt: Long): String {
    if (createdAt <= 0L) return ""
    return SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(createdAt))
}

private fun calculatePlaylistMinutes(tracks: List<Track>): Int {
    val totalSeconds = tracks.sumOf { parseTrackDurationSeconds(it.trackTime) }
    return totalSeconds / 60
}

private fun parseTrackDurationSeconds(trackTime: String): Int {
    val parts = trackTime.split(':')
    if (parts.size != 2) return 0
    val minutes = parts[0].toIntOrNull() ?: return 0
    val seconds = parts[1].toIntOrNull() ?: return 0
    return minutes * 60 + seconds
}

@Preview(showBackground = true, name = "Не найден")
@Composable
private fun PlaylistScreenPreviewNotFound() {
    PlaylistMakerTheme(dynamicColor = false) {
        PlaylistScreenContent(
            playlist = null,
            onTrackClick = {},
            onTrackLongClick = {},
            navigateBack = {},
        )
    }
}
