package com.practicum.playlistmaker.ui.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.navigation.PlayerNavigationArgs
import com.practicum.playlistmaker.ui.utils.TopAppButtonBar

@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val track = PlayerNavigationArgs.pendingTrack
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppButtonBar(
                context = context,
                text = stringResource(R.string.songs),
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
            if (track == null) {
                Text(
                    text = stringResource(R.string.player_no_track),
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
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
            }
        }
    }
}
