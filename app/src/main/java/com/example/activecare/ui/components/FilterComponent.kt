package com.example.activecare.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.ui.theme.AppTheme

@Composable
fun FilterComponent(
    onYearClicked: ()->Unit = {},
    onMonthClicked: ()->Unit = {},
    onWeekClicked: ()->Unit = {},
){
    val textSize = 22.sp
    val textColor = AppTheme.colors.LightText
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=24.dp, end = 24.dp, top = 24.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        TextComponent(
            text = stringResource(id = R.string.yearFilter),
            modifier = Modifier
                .clickable(
                    onClick = {
                        onYearClicked.invoke()
                    }
                ),
            textSize = textSize,
            textColor = textColor,
        )
        TextComponent(
            text = stringResource(id = R.string.monthFilter),
            modifier = Modifier
                .clickable(
                    onClick = {
                        onMonthClicked.invoke()
                    }
                ),
            textSize = textSize,
            textColor = textColor,
        )
        TextComponent(
            text = stringResource(id = R.string.weekFilter),
            modifier = Modifier
                .clickable(
                    onClick = {
                        onWeekClicked.invoke()
                    }
                ),
            textSize = textSize,
            textColor = textColor,
        )
    }
}