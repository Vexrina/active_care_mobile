package com.example.activecare.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.common.simpleDateTimeParser
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar


@Composable
fun ChooseDateComponent(
    date: Calendar,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    val currentDate = Calendar.getInstance()
    Row(
        modifier = modifier
            .padding(start = 24.dp, top = 20.dp, end = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CircleButton(
            modifier = Modifier
                .size(64.dp),
            onClick = onBackClick,
            next = false
        )
        TextComponent(
            text = simpleDateTimeParser(date).substring(0, 10),
            modifier = Modifier,
            textColor = AppTheme.colors.LightText,
            textSize = 22.sp,
        )
        CircleButton(
            modifier = Modifier
                .size(64.dp),
            onClick = onNextClick,
            color = if (isSameDate(date, currentDate)) Color.Gray else AppTheme.colors.LightBack
        )
    }
}

private fun isSameDate(date1: Calendar, date2: Calendar): Boolean {
    return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
            date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
            date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)
}

@Preview
@Composable
fun ChooseDateComponentPreview() {
    val currentDate by remember { mutableStateOf(Calendar.getInstance()) }

    ChooseDateComponent(
        date = currentDate,
        onBackClick = { currentDate.add(Calendar.DATE, -1) },
        onNextClick = {
            if (currentDate.before(Calendar.getInstance())) {
                currentDate.add(Calendar.DATE, 1)
            }
        }
    )
}