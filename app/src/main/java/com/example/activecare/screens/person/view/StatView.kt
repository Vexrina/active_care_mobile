package com.example.activecare.screens.person.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.common.simpleDateTimeParser
import com.example.activecare.ui.components.BoxedText
import com.example.activecare.ui.components.ChooseDateComponent
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.screens.person.models.PersonViewState
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun StatView(
    viewState: PersonViewState,
    date: Calendar = Calendar.getInstance(),
    onDataLoad: (Limitation) -> Unit,
    onChangeDate: (String) -> Unit,
) {
    var currentDate by remember { mutableStateOf(date) }
    var currentDateString by remember {
        mutableStateOf(simpleDateTimeParser(currentDate))
    }

    var stats by remember {
        mutableStateOf(viewState.stats)
    }

    LaunchedEffect(viewState.stats) {
        stats = viewState.stats
    }

    LaunchedEffect(currentDate) {
        onChangeDate.invoke(currentDateString)
        val newLimit = Limitation(
            date = currentDateString,
            date_offset = 0
        )
        onDataLoad.invoke(newLimit)
    }

    if (viewState.isLoad) {
        Box(
            Modifier.padding(100.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
            )
        }
    } else {
        ChooseDateComponent(
            date = currentDate,
            onBackClick = {
                val newDate = Calendar.getInstance()
                newDate.time = currentDate.time
                newDate.add(Calendar.DATE, -1)
                currentDate = newDate
                currentDateString = simpleDateTimeParser(currentDate)
            },
            onNextClick = {
                val newDate = Calendar.getInstance()
                newDate.time = currentDate.time
                newDate.add(Calendar.DATE, 1)
                if (newDate.before(Calendar.getInstance())) {
                    currentDate = newDate
                    currentDateString = simpleDateTimeParser(currentDate)
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, top = 12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            TextComponent(
                text = stringResource(id = R.string.Activity),
                modifier = Modifier,
                textAlign = TextAlign.Start,
                textColor = AppTheme.colors.LightText,
                textSize = 22.sp
            )
        }
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatActivityStep),
            value = "${stats.first.steps} шаг"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatActivityDistance),
            value = "${stats.first.distance} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatActivityCalories),
            value = "${stats.first.calories} ккал"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, top = 12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            TextComponent(
                text = stringResource(id = R.string.Measure),
                modifier = Modifier,
                textAlign = TextAlign.Start,
                textColor = AppTheme.colors.LightText,
                textSize = 22.sp
            )
        }
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatMeasureWeight),
            value = "${stats.second.weight} кг"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatMeasureSleep),
            value = "${stats.second.sleep / 100} ч. ${stats.second.sleep % 100} мин."
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatMeasurePulse),
            value = "${stats.second.pulse} уд/мин"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatMeasureSpO2),
            value = "${stats.second.spO2}%"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.StatMeasureCalories),
            value = "${stats.second.calories} ккал"
        )
    }
}
