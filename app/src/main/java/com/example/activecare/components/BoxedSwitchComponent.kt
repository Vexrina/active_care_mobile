package com.example.activecare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
fun BoxedSwitchComponent(
    modifier: Modifier,
    textModifier: Modifier,
    naming: String,
    textColor: Color = AppTheme.colors.LightText,
    textSize: TextUnit = 22.sp,
    textAlign1: TextAlign = TextAlign.Start,
    checked: Boolean,
    onCheckedClick: (Boolean) -> Unit = {},
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
                .width(200.dp),
            textColor = textColor,
            textSize = textSize,
            textAlign = textAlign1,
        )
        Switch(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            checked = checked,
            onCheckedChange = onCheckedClick,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Yellow,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray,
            )
        )
    }
}