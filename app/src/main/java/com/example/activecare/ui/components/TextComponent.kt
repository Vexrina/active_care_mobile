package com.example.activecare.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun TextComponent(
    text: String,
    modifier: Modifier,
    textColor: Color = AppTheme.colors.DarkText,
    textSize: TextUnit = 16.sp,
    textAlign: TextAlign = TextAlign.Center,
) {
    BasicText(
        text = text,
        style = TextStyle(
            fontSize = textSize,
            color = textColor,
            textAlign = textAlign,
        ),
        modifier = modifier,
    )
}