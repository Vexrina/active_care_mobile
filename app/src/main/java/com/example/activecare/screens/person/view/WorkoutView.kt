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
import com.example.activecare.components.BoxedText
import com.example.activecare.components.ChooseDateComponent
import com.example.activecare.components.TextComponent
import com.example.activecare.dataclasses.Limitation
import com.example.activecare.screens.person.models.PersonViewState
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun WorkoutView(
    viewState: PersonViewState,
    date: Calendar = Calendar.getInstance(),
    onDataLoad: (Limitation) -> Unit,
    onChangeDate: (String) -> Unit,
) {
    var currentDate by remember { mutableStateOf(date) }
    var currentDateString by remember {
        mutableStateOf(simpleDateTimeParser(currentDate))
    }

    var workouts by remember {
        mutableStateOf(viewState.workout)
    }

    LaunchedEffect(viewState.workout) {
        workouts = viewState.workout
    }

    LaunchedEffect(currentDate) {
        onChangeDate.invoke(currentDateString)
        val newLimit = Limitation(date = currentDateString, date_offset = 0)
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
                .padding(top = 12.dp, bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityTotalDistance),
            value = "${workouts.first.totalDistance} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityStreetRun),
            value = "${workouts.first.streetRun} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityTrackRun),
            value = "${workouts.first.trackRun} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityWalking),
            value = "${workouts.first.walking} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityBike),
            value = "${workouts.first.bike} км"
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
                .padding(top = 12.dp, bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutMeasurePulse),
            value = "${workouts.second.pulse} уд/мин"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutMeasureSpO2),
            value = "${workouts.second.spO2}%"
        )
    }
}