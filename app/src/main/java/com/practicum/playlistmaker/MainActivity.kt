package com.practicum.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.utils.ButtonSample
import com.practicum.playlistmaker.utils.IconType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuScreen()
        }
    }
}

@Composable
fun MenuScreen() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(55, 114, 231))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 16.dp),
            text = stringResource(R.string.app_name),
            fontSize = 22.sp,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.SansSerif
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                ),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                ButtonSample(
                    leadingIcon = IconType.PainterIcon(painterResource(R.drawable.search_icon)),
                    trailingIcon = IconType.ImageVectorIcon(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                    contentDescription = stringResource(R.string.search)
                ) {
                    val intent = Intent(context, SearchActivity::class.java)
                    context.startActivity(intent)
                }
                ButtonSample(
                    leadingIcon = IconType.PainterIcon(painterResource(R.drawable.playlists_icon)),
                    trailingIcon = IconType.ImageVectorIcon(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                    contentDescription = stringResource(R.string.playlists)
                ) {

                }
                ButtonSample(
                    leadingIcon = IconType.PainterIcon(painterResource(R.drawable.favourites_icon)),
                    trailingIcon = IconType.ImageVectorIcon(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                    contentDescription = stringResource(R.string.favourites)
                ) {

                }
                ButtonSample(
                    leadingIcon = IconType.PainterIcon(painterResource(R.drawable.settings_icon)),
                    trailingIcon = IconType.ImageVectorIcon(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                    contentDescription = stringResource(R.string.settings)
                ) {
                    val intent = Intent(context, SettingsActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    MenuScreen()
}