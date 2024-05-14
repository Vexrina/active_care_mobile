package com.example.activecare.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun ButtonComponent(
    text: String,
    modifier: Modifier,
    buttonColors: Color = AppTheme.colors.LightBack,
    textAlign: TextAlign = TextAlign.Center,
    textColor: Color = AppTheme.colors.LightText,
    textSize: TextUnit = 20.sp,
    radius: Int = 12,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColors),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                textAlign = textAlign,
                fontSize = textSize,
            )
        )
    }
}