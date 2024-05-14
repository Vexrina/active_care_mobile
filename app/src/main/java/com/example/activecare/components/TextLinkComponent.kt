package com.example.activecare.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun LinkComponent(
    commonText: String,
    linkText: String,
    textColor: Color = AppTheme.colors.LightText,
    textSize: TextUnit = 16.sp,
    rowModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    linkAction: () -> Unit,
) {
    Row(
        modifier = rowModifier,
    ) {
        TextComponent(
            text = "$commonText ",
            modifier = textModifier,
            textColor = textColor,
            textSize = textSize,
        )
        TextComponent(
            text = linkText,
            modifier = textModifier
                .clickable(onClick = linkAction),
            textColor = textColor,
            textSize = textSize
        )
    }
}