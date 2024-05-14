package com.example.activecare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun StepsComponent(
    currentProgress: Int,
    maxProgress: Int,
    ) {
    Row(
        modifier = Modifier
            .padding(start=24.dp, end=24.dp, top=8.dp)
            .border(
                width = 3.dp,
                color = AppTheme.colors.LightBack,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(start=24.dp)
        )
        {
            CircularProgressIndicator(
                progress = currentProgress.toFloat()/maxProgress,
                modifier = Modifier.size(120.dp),
                color = AppTheme.colors.DarkText,
                trackColor = AppTheme.colors.DarkBack
            )
            TextComponent(
                text = "$currentProgress/$maxProgress",
                modifier = Modifier,
                textColor = AppTheme.colors.LightText,
                textSize = 20.sp,
            )
        }
        TextComponent(
            text = "Шаги",
            modifier = Modifier.padding(start=100.dp),
            textColor = AppTheme.colors.LightText,
            textSize = 20.sp,
        )
    }
}

@Preview
@Composable
fun ShowStepsComponent() {
    StepsComponent(500, 10000)
}