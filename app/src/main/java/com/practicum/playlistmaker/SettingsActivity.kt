package com.practicum.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SettingsScreen() }
    }
}

@Composable
fun SettingsScreen() {
   val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                content = {
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(R.drawable.arrow_back_icon),
                        contentDescription = stringResource(R.string.arrow_back),
                        tint = Color.Black,
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 20.dp,
                    end = 28.dp,
                    bottom = 20.dp
                )
            )
            Text(
                stringResource(R.string.settings),
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth()
        ) {
            ButtonSample(
                painter = painterResource(R.drawable.theme_switch_icon),
                contentDescription = stringResource(R.string.dark_theme)
            ) { }
            ButtonSample(
                painter = painterResource(R.drawable.share_icon),
                contentDescription = stringResource(R.string.share_app)
            ) { }
            ButtonSample(
                painter = painterResource(R.drawable.support_icon),
                contentDescription = stringResource(R.string.message_to_support)
            ) { }
            ButtonSample(
                painter = painterResource(R.drawable.arrow_forward_icon),
                contentDescription = stringResource(R.string.user_agreement)
            ) { }
        }
    }
}

@Composable
private fun ButtonSample(painter: Painter, contentDescription: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        content = {
             Row(
                 verticalAlignment = Alignment.CenterVertically
             ) {
                 Text(
                     modifier = Modifier.weight(1f),
                     text = contentDescription,
                     color = Color.Black,
                     fontSize = 16.sp
                 )
                 if (contentDescription == stringResource(R.string.dark_theme)) {
                     Box(
                         contentAlignment = Alignment.Center
                     ) {
                         Icon(
                             painter = painter,
                             contentDescription = contentDescription,
                             tint = Color.LightGray
                         )
                         Icon(
                             painter = painterResource(R.drawable.knob_icon),
                             contentDescription = contentDescription,
                             tint = Color.Gray,
                             modifier = Modifier
                                 .size(24.dp)
                                 .align(Alignment.CenterStart)
                         )
                     }
                 } else {
                     Icon(
                         painter = painter,
                         contentDescription = contentDescription,
                         tint = Color.Gray
                     )
                 }
             }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(horizontal = 16.dp)
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}
