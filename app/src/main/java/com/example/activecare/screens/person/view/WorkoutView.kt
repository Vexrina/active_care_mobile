package com.example.activecare.screens.person.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.BoxedText
import com.example.activecare.components.TextComponent
import com.example.activecare.screens.person.models.PersonViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun WorkoutView(
    viewState: PersonViewState,
    chooseDate: () -> Unit,
) {
    if (viewState.workout == null) {
        Box(
            Modifier.padding(100.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
            )
        }
    } else {
        Row(
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextComponent(
                text = viewState.workout.first.dateStamp,
                modifier = Modifier
                    .padding(start = 48.dp),
                textSize = 22.sp,
                textColor = AppTheme.colors.LightText,
                textAlign = TextAlign.Start,
            )
            Icon(
                painter = painterResource(id = R.drawable.triangle),
                contentDescription = "go workout",
                tint = AppTheme.colors.LightText,
                modifier = Modifier
                    .padding(start = 12.dp, top = 6.dp)
                    .size(16.dp)
                    .clickable(onClick = chooseDate),
            )
        }
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
            value = "${viewState.workout.first.totalDistance} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityStreetRun),
            value = "${viewState.workout.first.streetRun} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityTrackRun),
            value = "${viewState.workout.first.trackRun} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityWalking),
            value = "${viewState.workout.first.walking} км"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutActivityBike),
            value = "${viewState.workout.first.bike} км"
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
            value = "${viewState.workout.second.pulse} уд/мин"
        )
        BoxedText(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .width(328.dp),
            textModifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            naming = stringResource(id = R.string.WorkoutMeasureSpO2),
            value = "${viewState.workout.second.spO2}%"
        )
    }
}