package com.practicum.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.utils.TopAppButtonBar

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SearchScreen() }
    }
}

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var searchRequest by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppButtonBar(
                context = context,
                text =stringResource(R.string.search),
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    ) { paddingValues ->
        CustomSearchField(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = searchRequest,
            onValueChange = { searchRequest = it },
            placeholder = stringResource(R.string.search),
            onClearClick = { searchRequest = "" }
        )
    }
}

@Composable
fun CustomSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onClearClick: () -> Unit = {},
    height: Dp = 36.dp,
    iconTextSpacing: Dp = 8.dp,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 8.dp,
    iconSize: Dp = 16.dp,
    textSize: TextUnit = 16.sp
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.LightGray, MaterialTheme.shapes.small)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Icons.Default.Search,
                contentDescription = placeholder,
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(iconTextSpacing))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = verticalPadding),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    modifier = Modifier.fillMaxSize(),
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
                            contentAlignment = Alignment.CenterStart
                        ) {
                            // Placeholder
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    fontSize = textSize,
                                    color = Color.Gray,
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .clickable(onClick = onClearClick),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}

@Composable
private fun TextFieldSearchScreen() {
    val context = LocalContext.current
    var searchRequest: String by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppButtonBar(
                context = context,
                text = stringResource(R.string.search),
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    ) { paddingValues ->
        OutlinedTextField(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = searchRequest,
            onValueChange = { searchRequest = it },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.search),
                    fontSize = 16.sp,
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            trailingIcon = {
                if (!searchRequest.isEmpty()) {
                    Icon(
                        modifier = Modifier
                            .size(14.dp)
                            .clickable(
                                onClick = { searchRequest = "" }
                            ),
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.search),
                        tint = Color.Gray
                    )
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedLeadingIconColor = Color.Gray,
                unfocusedLeadingIconColor = Color.Gray,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
        )
    }
}

@Preview
@Composable
private fun TextFieldSearchScreenPreview() {
    TextFieldSearchScreen()
}
