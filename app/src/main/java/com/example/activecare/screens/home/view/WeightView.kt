package com.example.activecare.screens.home.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.common.calculateEndDate
import com.example.activecare.common.earlyThanDate
import com.example.activecare.common.filterByDate
import com.example.activecare.common.simpleDateTimeParser
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.ChooseDateComponent
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.components.TextFieldComponent
import com.example.activecare.ui.components.WeightChangesComponent
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun WeightView(
    viewState: HomeViewState,
    date: Calendar,
    limit: Limitation,
    onDataLoad: (Limitation)->Unit,
    onChangeDate: (String)->Unit,
    value: String?,
    onValueChange: (String)->Unit,
    onSendData: ()->Unit,
){
    val focusManager = LocalFocusManager.current
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
    LaunchedEffect(currentDateString){
        onChangeDate.invoke(currentDateString)
        Log.d("WVLE", currentDateString)
        if (currentDateString.substring(0,10) == calculateEndDate(limit).substring(0,10)){
            Log.d("WVLE", "ALARM")
            val newLimit = Limitation(date = currentDateString)
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
            currentDateString = simpleDateTimeParser(currentDate)
            stats = filterByDate(viewState.stats, currentDate)
        },
        onNextClick = {
            val newDate = Calendar.getInstance()
            newDate.time = currentDate.time
            newDate.add(Calendar.DATE, 1)
            if (newDate.before(Calendar.getInstance())) {
                currentDate = newDate
                currentDateString = simpleDateTimeParser(currentDate)
                stats = filterByDate(viewState.stats, currentDate)
            }
        }
    )
    Log.d("WV", "${stats.size}")
    if (stats.size>1){
        WeightChangesComponent(
            lastWeight = stats[0].weight,
            preLastWeight = stats[1].weight
        )
    } else {
        val newStats = earlyThanDate(viewState.stats, currentDate)
        if (newStats.size>1){
            WeightChangesComponent(
                lastWeight = newStats[0].weight,
                preLastWeight = newStats[1].weight
            )
        } else if (newStats.size==1){
            WeightChangesComponent(
                lastWeight = newStats[0].weight,
                preLastWeight = newStats[0].weight
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextFieldComponent(
            value = value ?: "",
            onValueChange = { onValueChange(it) },
            label = stringResource(id = R.string.weightHint),
            modifier = Modifier
                .width(150.dp)
                .height(100.dp),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        TextComponent(
            text = "КГ",
            modifier = Modifier
                .padding(start = 16.dp),
            textSize = 30.sp,
            textColor = AppTheme.colors.LightText
        )
    }
    ButtonComponent(
        text = stringResource(id = R.string.addWeight),
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        onSendData.invoke()
    }
}