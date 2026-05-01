package com.practicum.playlistmaker.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.practicum.playlistmaker.data.network.Playlist
import com.practicum.playlistmaker.ui.utils.TopAppButtonBar
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick.invoke() },
                onLongClick = { onLongClick.invoke() },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val coverModel = playlist.coverPath.takeIf { it.isNotBlank() }?.let(::File)
        Surface(
            modifier = Modifier.size(48.dp),
            color = Color.LightGray,
        ) {
            if (coverModel == null) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_music),
                    contentDescription = playlist.name,
                )
            } else {
                AsyncImage(
                    model = coverModel,
                    contentDescription = playlist.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(playlist.name, fontSize = 16.sp)
            val text = pluralStringResource(
                id = R.plurals.tracks_count,
                count = playlist.tracks.size,
                playlist.tracks.size,
            )
            Text(text, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PlaylistsScreen(
    modifier: Modifier,
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList<Playlist>())
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var playlistToDelete by remember { mutableStateOf<Playlist?>(null) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppButtonBar(
                text = stringResource(R.string.playlists),
                context = context,
                onClick = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addNewPlaylist() },
                containerColor = Color.Gray,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_playlist)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(playlists.size) { index ->
                    PlaylistListItem(
                        playlist = playlists[index],
                        onClick = { navigateToPlaylist(playlists[index].id) },
                        onLongClick = { playlistToDelete = playlists[index] },
                    )
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
    }

    if (playlistToDelete != null) {
        AlertDialog(
            onDismissRequest = { playlistToDelete = null },
            title = { Text(text = stringResource(R.string.delete_playlist_title)) },
            text = { Text(text = stringResource(R.string.delete_playlist_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        val id = playlistToDelete?.id
                        playlistToDelete = null
                        if (id != null) {
                            coroutineScope.launch {
                                playlistsViewModel.deletePlaylistById(id)
                            }
                        }
                    },
                ) {
                    Text(text = stringResource(R.string.delete_action))
                }
            },
            dismissButton = {
                TextButton(onClick = { playlistToDelete = null }) {
                    Text(text = stringResource(R.string.cancel_action))
                }
            },
        )
    }
}

@Preview
@Composable
private fun PlaylistsScreenPreview(){
    val playlistsViewModel = PlaylistsViewModel()
    PlaylistsScreen(modifier = Modifier, playlistsViewModel = playlistsViewModel, addNewPlaylist = { }, navigateToPlaylist = { }, navigateBack = { })
}