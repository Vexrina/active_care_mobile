package com.example.activecare.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    next: Boolean = true,
    plus: Boolean = false,
    color: Color = AppTheme.colors.LightBack,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(containerColor = color),
    ) {
        Icon(
            imageVector = if (plus){
                Icons.Outlined.AddCircleOutline
            } else if (next) {
                Icons.AutoMirrored.Filled.ArrowForward
            } else {
                Icons.AutoMirrored.Filled.ArrowBack
            },
            contentDescription = if (next) {
                "go next"
            } else {
                "go back"
            },
            tint = AppTheme.colors.LightText,
            modifier = Modifier.size(60.dp),
        )
    }
}

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    pause: Boolean = false,
    play: Boolean = false,
    stop: Boolean = false,
    color: Color = AppTheme.colors.LightBack,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(containerColor = if (stop) Color(0xFFFF4433) else color),
    ) {
        Icon(
            imageVector = if (pause){
                Icons.Outlined.Pause
            } else if (play) {
                Icons.Filled.PlayArrow
            } else {
                Icons.Filled.Stop
            },
            contentDescription = if (pause){
                "pause workout"
            } else if (play) {
                "continue workout"
            } else {
                "stop workout"
            },
            tint = if (stop) Color.White else AppTheme.colors.LightText,
            modifier = Modifier.size(80.dp),
        )
    }
}