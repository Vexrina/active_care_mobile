package com.example.activecare.screens.home.view

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.common.calculateEndDate
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.common.filterByDate
import com.example.activecare.common.simpleDateTimeParser
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.ChooseDateComponent
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun WaterView(
    viewState: HomeViewState,
    onChangeWater: (Int) -> Unit,
    date: Calendar,
    limit: Limitation,
    onDataLoad: (Limitation) -> Unit,
    onChangeDate: (String) -> Unit,
) {
    var currentDate by remember { mutableStateOf(date) }

    var currentDateString by remember {
        mutableStateOf(simpleDateTimeParser(currentDate))
    }
    var endDate by remember {
        mutableStateOf(calculateEndDate(limit).substring(0, 10))
    }

    var stats by remember {
        mutableStateOf(filterByDate(viewState.stats, currentDate))
    }
    LaunchedEffect(currentDateString) {
        onChangeDate.invoke(currentDateString)
        Log.d("WVLE", currentDateString)
        if (currentDateString.substring(0, 10) == calculateEndDate(limit).substring(0, 10)) {
            Log.d("WVLE", "ALARM")
            val newLimit =
                Limitation(date = currentDateString)
            endDate = calculateEndDate(newLimit)
            onDataLoad.invoke(newLimit)
        }
    }
    ChooseDateComponent(
        date = currentDate,
        onBackClick = {
            val newDate = Calendar.getInstance()
            newDate.time = currentDate.time
            newDate.add(Calendar.DATE, -1)
            currentDate = newDate
        },
        onNextClick = {
            val newDate = Calendar.getInstance()
            newDate.time = currentDate.time
            newDate.add(Calendar.DATE, 1)
            if (newDate.before(Calendar.getInstance())) {
                currentDate = newDate
            }
        }
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(top = 48.dp)
    ) {
        items(8) { idx ->
            Icon(
                painter = if (idx < stats[0].water)
                    painterResource(id = R.drawable.water_filled)
                else painterResource(id = R.drawable.water_outlined),
                contentDescription = if (idx < stats[0].water)
                    idx.toString() + stringResource(id = R.string.water_filled)
                else idx.toString() + stringResource(id = R.string.water_outlined),
                tint = AppTheme.colors.LightText,
                modifier = Modifier.size(80.dp)
            )
        }
    }
    TextComponent(
        text = stringResource(id = R.string.waterInfo),
        modifier = Modifier.padding(24.dp),
        textSize = 22.sp,
        textColor = AppTheme.colors.LightText,
        textAlign = TextAlign.Start,
    )
    ButtonComponent(
        text = "Стакан выпит",
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (stats[0].water < 8) onChangeWater.invoke(1)
    }
    ButtonComponent(
        text = "Стакан не выпит",
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (stats[0].water < 8) onChangeWater.invoke(-1)
    }
}