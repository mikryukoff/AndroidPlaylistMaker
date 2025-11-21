package com.practicum.playlistmaker.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonSample(
    leadingIcon: IconType? = null,
    trailingIcon: IconType? = null,
    contentFontSize: Int = 22,
    horizontalPadding: Int = 28,
    verticalPadding: Int = 20,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                leadingIcon?.let { icon -> CorrectIcon(icon, contentDescription, Color.Black) }

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = contentDescription,
                    fontSize = contentFontSize.sp,
                )

                trailingIcon?.let { icon -> CorrectIcon(icon, contentDescription, Color.Gray)}
            }
        },
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(vertical = verticalPadding.dp, horizontal = horizontalPadding.dp)
    )
}
