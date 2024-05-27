package com.example.activecare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun CardComponent(
    header: String,
    value: String,
    onClick: () -> Unit = {},
    containerColor: Color = Color.Gray,
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, top = 22.dp, end = 20.dp)
            .height(124.dp)
            .width(158.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        TextComponent(
            text = header,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            textColor = AppTheme.colors.LightText,
            textSize = 22.sp,
        )
        TextComponent(
            text = value,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            textColor = AppTheme.colors.LightText,
            textSize = 22.sp,
        )
    }
}