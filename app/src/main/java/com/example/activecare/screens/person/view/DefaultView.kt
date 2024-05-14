package com.example.activecare.screens.person.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.TextComponent
import com.example.activecare.screens.person.models.PersonViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun DefaultView(
    viewState: PersonViewState,
    onStatViewClick: () -> Unit,
    onWorkoutViewClick: () -> Unit,
    onSettingClick: () -> Unit,
    onDevicesClick: () -> Unit,
) {
    val height = 56.dp
    val width = 328.dp
    Box(
        modifier = Modifier
            .padding(top = 24.dp)
            .width(width)
            .height(height)
            .clip(shape = RoundedCornerShape(12))
            .background(AppTheme.colors.LightBack),
        contentAlignment = Alignment.CenterStart
    ) {
        TextComponent(
            text = if (viewState.user == null) "Unknown" else viewState.user.username,
            modifier = Modifier.align(Alignment.Center),
            textColor = AppTheme.colors.LightText,
            textSize = 22.sp,
        )
    }
    ButtonComponent(
        text = stringResource(id = R.string.MyStatButton),
        modifier = Modifier
            .padding(top = 48.dp)
            .width(width)
            .height(height),
        textAlign = TextAlign.Start,
    ) {
        onStatViewClick.invoke()
    }
    ButtonComponent(
        text = stringResource(id = R.string.MyWorkoutButton),
        modifier = Modifier
            .padding(top = 16.dp)
            .width(width)
            .height(height),
        textAlign = TextAlign.Start,
    ) {
        onWorkoutViewClick.invoke()
    }
    ButtonComponent(
        text = stringResource(id = R.string.MyRecsButton),
        modifier = Modifier
            .padding(top = 16.dp)
            .width(width)
            .height(height),
        textAlign = TextAlign.Start,
    ) {

    }
    ButtonComponent(
        text = stringResource(id = R.string.MyDevicesButton),
        modifier = Modifier
            .padding(top = 16.dp)
            .width(width)
            .height(height),
        textAlign = TextAlign.Start,
    ) {
        onDevicesClick.invoke()
    }
    ButtonComponent(
        text = stringResource(id = R.string.MySettingsButton),
        modifier = Modifier
            .padding(top = 68.dp)
            .width(width)
            .height(height),
        textAlign = TextAlign.Start,
    ) {
        onSettingClick.invoke()
    }
}