package com.practicum.playlistmaker.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.network.Track
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme
import com.practicum.playlistmaker.ui.utils.CorrectIcon
import com.practicum.playlistmaker.ui.utils.IconType
import com.practicum.playlistmaker.ui.utils.TopAppButtonBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    onTrackClick: (Track) -> Unit,
    onBackClick: () -> Unit,
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    BackHandler(onBack = onBackClick)

    LaunchedEffect(text) {
        searchViewModel.updateQuery(text)
    }

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> focusManager.clearFocus()
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        historyList = searchViewModel.getHistoryList()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppButtonBar(
                context = context,
                text = stringResource(R.string.search),
                onClick = onBackClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            CustomSearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = text,
                onValueChange = { text = it },
                placeholder = stringResource(R.string.search),
                clearContentDescription = stringResource(R.string.clear_search),
                onClearClick = {
                    text = ""
                    focusManager.clearFocus()
                    searchViewModel.clearSearch()
                },
                onSearchClick = { },
                focusRequester = focusRequester,
                onFocusChange = { focused -> isFocused = focused },
            )

            if (isFocused && text.isEmpty() && historyList.isNotEmpty()) {
                HistoryRequests(
                    historyList = historyList,
                    onClick = { word -> text = word },
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                when (val state = screenState) {
                    is SearchState.Initial -> {
                        if (text.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    stringResource(R.string.search),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is SearchState.Searching -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is SearchState.Success -> {
                        val tracks = state.foundList
                        if (tracks.isEmpty()) {
                            SearchPlaceholder(
                                message = stringResource(R.string.no_songs_found),
                                icon = IconType.PainterIcon(painterResource(R.drawable.music_not_found))
                            )
                        } else {
                            LazyColumn {
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

                    is SearchState.Fail -> {
                        SearchPlaceholder(
                            message = stringResource(R.string.server_error),
                            icon = IconType.PainterIcon(painterResource(R.drawable.search_error)),
                            extraDetail = state.error,
                            onRetry = { searchViewModel.retryLastSearch() },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchPlaceholder(
    message: String,
    icon: IconType,
    modifier: Modifier = Modifier,
    extraDetail: String? = null,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CorrectIcon(
            icon = icon,
            contentDescription = "icon",
            tint = Color.Unspecified,
            size = 120,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        extraDetail?.takeIf { it.isNotBlank() }?.let { detail ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = detail,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        onRetry?.let { retry ->
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = retry) {
                Text(stringResource(R.string.refresh))
            }
        }
    }
}

@Composable
fun CustomSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    clearContentDescription: String = "",
    onClearClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    onFocusChange: (Boolean) -> Unit = {},
    height: Dp = 36.dp,
    iconTextSpacing: Dp = 8.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 8.dp,
    iconSize: Dp = 16.dp,
    textSize: TextUnit = 16.sp,
) {
    Box(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.LightGray, MaterialTheme.shapes.small),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .clickable(onClick = onSearchClick),
                imageVector = Icons.Default.Search,
                contentDescription = placeholder,
                tint = Color.Gray,
            )

            Spacer(modifier = Modifier.width(iconTextSpacing))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = verticalPadding),
                contentAlignment = Alignment.CenterStart,
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            onFocusChange(focusState.isFocused)
                        },
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = textSize,
                        color = Color.Black,
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    fontSize = textSize,
                                    color = Color.Gray,
                                )
                            }
                            innerTextField()
                        }
                    },
                )
            }
            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .clickable(onClick = onClearClick),
                    imageVector = Icons.Default.Clear,
                    contentDescription = clearContentDescription,
                    tint = Color.Gray,
                )
            }
        }
    }
}

@Composable
fun TrackListItem(
    track: Track,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
) {
    val placeholder = painterResource(id = R.drawable.ic_music)
    val artworkUrl = track.image.takeIf { it.isNotBlank() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(horizontal = 13.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = artworkUrl,
            contentDescription = null,
            modifier = Modifier.size(45.dp),
            placeholder = placeholder,
            error = placeholder,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = track.trackName,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${track.artistName} · ${track.trackTime}",
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
        )
    }
}

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp),
            ),
    ) {
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = historyList[index])
            }
            if (historyList[index] != historyList.last()) {
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}

@Preview
@Composable
private fun TrackListItemPreview() {
    TrackListItem(
        track = Track(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "2:38",
            image = "",
            favorite = false,
            playlistId = 0,
        ),
        onClick = {},
    )
}

@Preview
@Composable
private fun CustomSearchFieldPreview() {
    CustomSearchField(
        value = "",
        onValueChange = {},
        placeholder = "Поиск",
        clearContentDescription = "",
    )
}

@Preview(showBackground = true, name = "Search screen")
@Composable
private fun SearchScreenPreview() {
    val previewRepository = object : TracksRepository {
        override suspend fun searchTracks(expression: String): List<Track> = emptyList()

        override fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flowOf(null)

        override fun getFavoriteTracks(): Flow<List<Track>> = flowOf(emptyList())

        override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) = Unit

        override suspend fun deleteTracksByPlaylistId(playlistId: Long) = Unit

        override suspend fun deleteTrackFromPlaylist(track: Track) = Unit

        override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) = Unit
    }
    val previewSearchHistoryRepository = object : SearchHistoryRepository {
        override suspend fun getHistoryRequests(): List<String> = emptyList()
        override fun addToHistory(word: com.practicum.playlistmaker.data.network.Word) = Unit
    }

    PlaylistMakerTheme(dynamicColor = false) {
        SearchScreen(
            searchViewModel = SearchViewModel(
                tracksRepository = previewRepository,
                searchHistoryRepository = previewSearchHistoryRepository,
            ),
            onTrackClick = {},
            onBackClick = {},
        )
    }
}
