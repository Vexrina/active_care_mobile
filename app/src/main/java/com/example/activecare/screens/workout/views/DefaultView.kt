package com.example.activecare.screens.workout.views

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.activecare.R
import com.example.activecare.ui.components.ButtonComponent

@Composable
fun DefaultView(
    onStreetRunClick: ()->Unit = {},
    onTrackRunClick: ()->Unit = {},
    onWalkingClick: ()->Unit = {},
    onBikeClick: ()->Unit = {},
){
    val buttonModifier = Modifier
        .padding(top=20.dp)
        .width(328.dp)
        .height(60.dp)
    ButtonComponent(
        text = stringResource(id = R.string.StreetRun),
        modifier = Modifier.padding(top=16.dp)
            .then(buttonModifier),
        onClick = {onStreetRunClick.invoke()},
    )
    ButtonComponent(
        text = stringResource(id = R.string.TrackRun),
        modifier = buttonModifier,
        onClick = {onTrackRunClick.invoke()},
    )
    ButtonComponent(
        text = stringResource(id = R.string.Walking),
        modifier = buttonModifier,
        onClick = {onWalkingClick.invoke()},
    )
    ButtonComponent(
        text = stringResource(id = R.string.Bike),
        modifier = buttonModifier,
        onClick = {onBikeClick.invoke()},
    )
}