package com.practicum.playlistmaker.utils

import androidx.annotation.Size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R

sealed class IconType {
    data class ImageVectorIcon(val imageVector: ImageVector) : IconType()
    data class PainterIcon(val painter: Painter) : IconType()
}

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

@Composable
private fun CorrectIcon(
    icon: IconType,
    contentDescription: String,
    tint: Color = Color.Gray,
    size: Int = 24
) {
    when (icon) {
        is IconType.PainterIcon -> {
            if (contentDescription == stringResource(R.string.dark_theme)) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(height = 12.dp, width = 35.dp),
                        painter = icon.painter,
                        contentDescription = contentDescription,
                        tint = Color.LightGray
                    )
                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.CenterStart),
                        painter = painterResource(R.drawable.knob_icon),
                        contentDescription = contentDescription,
                        tint = Color.Gray,
                    )
                }
            } else {
                Icon(
                    modifier = Modifier
                        .size(size.dp),
                    painter = icon.painter,
                    contentDescription = contentDescription,
                    tint = tint
                )
            }
        }
        is IconType.ImageVectorIcon -> {
            Icon(
                modifier = Modifier
                    .size(size.dp),
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}