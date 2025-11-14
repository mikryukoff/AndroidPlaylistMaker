package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.practicum.playlistmaker.utils.ButtonSample
import com.practicum.playlistmaker.utils.IconType
import com.practicum.playlistmaker.utils.TopAppButtonBar
import androidx.core.net.toUri

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SettingsScreen() }
    }
}

@Composable
fun SettingsScreen() {
   val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppButtonBar(
                context = context,
                text =stringResource(R.string.settings),
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            ButtonSample(
                trailingIcon = IconType.PainterIcon(painterResource(R.drawable.theme_switch_icon)),
                contentDescription = stringResource(R.string.dark_theme),
                contentFontSize = 16,
                horizontalPadding = 16,
            ) { }
            ButtonSample(
                trailingIcon = IconType.ImageVectorIcon(Icons.Default.Share),
                contentDescription = stringResource(R.string.share_app),
                contentFontSize = 16,
                horizontalPadding = 16,
            ) {
                val shareIntent: Intent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download my app!")
                shareIntent.setType("text/plain")
                context.startActivity(shareIntent)
            }
            ButtonSample(
                trailingIcon = IconType.PainterIcon(painterResource(R.drawable.support_icon)),
                contentDescription = stringResource(R.string.message_to_support),
                contentFontSize = 16,
                horizontalPadding = 16,
            ) {
                val supportMessageIntent: Intent = Intent(Intent.ACTION_SENDTO)
                supportMessageIntent.data = "mailto:".toUri()
                supportMessageIntent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(context.getString(R.string.dev_email))
                )
                supportMessageIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    context.getString(R.string.email_subject)
                )
                supportMessageIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    context.getString(R.string.email_text)
                )
                context.startActivity(supportMessageIntent)
            }
            ButtonSample(
                trailingIcon = IconType.PainterIcon(painterResource(R.drawable.arrow_forward_icon)),
                contentDescription = stringResource(R.string.user_agreement),
                contentFontSize = 16,
                horizontalPadding = 16,
            ) {
                val userAgreementIntent: Intent = Intent(Intent.ACTION_VIEW)
                userAgreementIntent.data = context.getString(R.string.agreement_url).toUri()
                context.startActivity(userAgreementIntent)
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}
