package com.practicum.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SearchScreen() }
    }
}

@Composable
fun SearchScreen() {
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
                stringResource(R.string.search),
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 64.dp, horizontal = 16.dp),
            onClick = { },
            content = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = stringResource(R.string.search)
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    text = stringResource(R.string.search),
                    fontSize = 16.sp
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Gray
            ),
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(start = 12.dp)
        )
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}