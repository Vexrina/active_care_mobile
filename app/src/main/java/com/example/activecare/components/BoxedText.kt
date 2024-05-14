package com.example.activecare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun BoxedText(
    modifier: Modifier,
    textModifier: Modifier,
    naming: String,
    value: String,
    textColor: Color = AppTheme.colors.LightText,
    textSize: TextUnit = 22.sp,
    textAlign1: TextAlign = TextAlign.Start,
    textAlign2: TextAlign = TextAlign.End,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12))
            .background(AppTheme.colors.LightBack)
    ) {
        TextComponent(
            text = naming,
            modifier = textModifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .width(164.dp),
            textColor = textColor,
            textSize = textSize,
            textAlign = textAlign1,
        )
        TextComponent(
            text = value,
            modifier = textModifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .width(164.dp),
            textColor = textColor,
            textSize = textSize,
            textAlign = textAlign2,
        )
    }
}