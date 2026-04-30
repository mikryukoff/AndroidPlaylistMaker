package com.practicum.playlistmaker.ui.player

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.ui.navigation.PlayerNavigationArgs
import com.practicum.playlistmaker.ui.playlist.PlaylistsViewModel
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme
import com.practicum.playlistmaker.ui.utils.TopAppButtonBar
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    playlistsViewModel: PlaylistsViewModel,
    navigateBack: () -> Unit,
) {
    val initialTrack = PlayerNavigationArgs.pendingTrack
    val context = LocalContext.current
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()
    var showPlaylistSheet by remember { mutableStateOf(false) }
    var currentTrack by remember(initialTrack?.id) { mutableStateOf(initialTrack) }

    LaunchedEffect(initialTrack?.id) {
        initialTrack?.let { track ->
            val actualTrack = playlistsViewModel.isExist(track)
            currentTrack = actualTrack ?: track
        }
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppButtonBar(
                context = context,
                text = stringResource(R.string.track_details),
                onClick = {
                    PlayerNavigationArgs.pendingTrack = null
                    navigateBack()
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
        ) {
            if (currentTrack == null) {
                Text(
                    text = stringResource(R.string.player_no_track),
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
                val track = currentTrack ?: return@Column
                val artworkUrl = track.image.takeIf { it.isNotBlank() }
                AsyncImage(
                    model = artworkUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(bottom = 24.dp),
                    placeholder = painterResource(R.drawable.ic_music),
                    error = painterResource(R.drawable.music_not_found),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = track.trackName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Text(
                    text = track.artistName,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Text(
                    text = track.trackTime,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 54.dp),
                ) {
                    IconButton(
                        onClick = {
                            val newFavoriteValue = !track.favorite
                            currentTrack = track.copy(favorite = newFavoriteValue)
                            coroutineScope.launch {
                                playlistsViewModel.toggleFavorite(
                                    track = track,
                                    isFavorite = newFavoriteValue,
                                )
                                Toast.makeText(
                                    context,
                                    context.getString(
                                        if (newFavoriteValue) R.string.added_to_favorites_message
                                        else R.string.removed_from_favorites_message,
                                    ),
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        },
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = if (track.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(R.string.add_to_favorites),
                            tint = if (track.favorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    IconButton(
                        onClick = { showPlaylistSheet = true }
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add_to_playlist),
                        )
                    }
                }
            }
        }
    }

    if (showPlaylistSheet) {
        ModalBottomSheet(onDismissRequest = { showPlaylistSheet = false }) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.select_playlist),
                style = MaterialTheme.typography.titleMedium,
            )
            if (playlists.isEmpty()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.playlists_empty),
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    items(playlists.size) { index ->
                        PlaylistRow(
                            playlist = playlists[index],
                            onClick = {
                                currentTrack?.let { trackForPlaylist ->
                                    coroutineScope.launch {
                                        playlistsViewModel.insertTrackToPlaylist(
                                            track = trackForPlaylist,
                                            playlistId = playlists[index].id,
                                        )
                                        currentTrack = trackForPlaylist.copy(playlistId = playlists[index].id)
                                        Toast.makeText(
                                            context,
                                            context.getString(
                                                R.string.added_to_playlist_message,
                                                playlists[index].name,
                                            ),
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                        showPlaylistSheet = false
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistRow(
    playlist: Playlist,
    onClick: () -> Unit,
) {
    val coverModel = playlist.coverPath.takeIf { it.isNotBlank() }?.let(::File)
    val tracksText = pluralStringResource(
        id = R.plurals.tracks_count,
        count = playlist.tracks.size,
        playlist.tracks.size,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            if (coverModel == null) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_music),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                AsyncImage(
                    model = coverModel,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = playlist.name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = tracksText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true, name = "Track details")
@Composable
private fun PlayerScreenPreview() {
    PlayerNavigationArgs.pendingTrack = Track(
        id = 1L,
        trackName = "Billie Jean",
        artistName = "Michael Jackson",
        trackTime = "4:54",
        image = "",
        favorite = false,
        playlistId = 0L,
    )
    PlaylistMakerTheme(dynamicColor = false) {
        PlayerScreen(
            playlistsViewModel = PlaylistsViewModel(),
            navigateBack = {},
        )
    }
}
