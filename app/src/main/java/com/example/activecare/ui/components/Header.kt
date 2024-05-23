package com.example.activecare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun Header(
    text: String,
    modifier: Modifier,
    backgroundColor: Color = AppTheme.colors.LightBack,
    backIcon: Boolean = false,
    textSize: TextUnit = 17.sp,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        if (backIcon) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "go back",
                tint = AppTheme.colors.LightText,
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .size(24.dp)
                    .clickable(onClick = onClick)
                    .align(Alignment.TopStart),
            )
        }
        Text(
            text = text,
            style = TextStyle(
                color = AppTheme.colors.LightText,
                fontSize = textSize,
                textAlign = TextAlign.Center,
            ),
            modifier = modifier
                .align(Alignment.Center)
        )
    }
}