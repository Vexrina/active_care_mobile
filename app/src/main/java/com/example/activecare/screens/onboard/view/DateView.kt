package com.example.activecare.screens.onboard.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.common.DateTimeParser
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.theme.AppTheme
import java.time.Instant
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateView(onDateChanged: (String) -> Unit) {
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        val selectedDate = datePickerState.selectedDateMillis?.let {
            Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
        }
        val selectedDateString = selectedDate?.format(DateTimeParser).toString()
        Log.d("MapView", selectedDateString)
        onDateChanged(selectedDateString)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = stringResource(id = R.string.onboardHintAge),
            modifier = Modifier
                .padding(top = 12.dp),
            textSize = 30.sp,
            textColor = AppTheme.colors.LightText
        )
        DatePicker(
            state = datePickerState,
            title = {},
            modifier = Modifier.wrapContentWidth(),
            colors = DatePickerDefaults.colors(
                headlineContentColor = AppTheme.colors.LightText,
                dayContentColor = AppTheme.colors.LightText,
                yearContentColor = AppTheme.colors.LightText,
                currentYearContentColor = AppTheme.colors.LightText,
                selectedYearContentColor = AppTheme.colors.LightText,
                selectedYearContainerColor = AppTheme.colors.LightText,
                weekdayContentColor = AppTheme.colors.LightText,
                disabledDayContentColor = AppTheme.colors.DarkText,
                selectedDayContainerColor = AppTheme.colors.DarkText,
                selectedDayContentColor = AppTheme.colors.LightText,
                subheadContentColor = AppTheme.colors.DarkText
            )
        )
    }
}