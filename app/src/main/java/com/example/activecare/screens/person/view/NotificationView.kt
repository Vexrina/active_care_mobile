package com.example.activecare.screens.person.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.activecare.R
import com.example.activecare.components.BoxedSwitchComponent

@Composable
fun NotificationView() {
    var checked by remember { mutableStateOf(true) }
    var checked1 by remember { mutableStateOf(true) }
    var checked2 by remember { mutableStateOf(false) }
    BoxedSwitchComponent(
        modifier = Modifier
            .width(328.dp)
            .padding(top = 28.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.NotificationWater),
        checked = checked,
        onCheckedClick = {
            checked = it
        }
    )
    BoxedSwitchComponent(
        modifier = Modifier
            .width(328.dp)
            .padding(top = 28.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.NotificationStandsUp),
        checked = checked1,
        onCheckedClick = {
            checked1 = it
        }
    )
    BoxedSwitchComponent(
        modifier = Modifier
            .width(328.dp)
            .padding(top = 28.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.NotificationWeightsChange),
        checked = checked2,
        onCheckedClick = {
            checked2 = it
        }
    )
}