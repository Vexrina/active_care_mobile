package com.example.activecare.screens.person.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.ui.components.ButtonComponent

@Composable
fun SettingView(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onDevicesClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    ButtonComponent(
        text = stringResource(id = R.string.SettingsProfile),
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(56.dp),
        textSize = 22.sp,
        textAlign = TextAlign.Start,
    ) {
        onProfileClick.invoke()
    }
    ButtonComponent(
        text = stringResource(id = R.string.SettingsNotifications),
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(56.dp),
        textSize = 22.sp,
        textAlign = TextAlign.Start,
    ) {
        onNotificationClick.invoke()
    }
    ButtonComponent(
        text = stringResource(id = R.string.SettingsDevices),
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(56.dp),
        textSize = 22.sp,
        textAlign = TextAlign.Start,
    ) {
        onDevicesClick.invoke()
    }
    ButtonComponent(
        text = stringResource(id = R.string.LogOut),
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(56.dp),
        textSize = 22.sp,
        textAlign = TextAlign.Start,
    ) {
        onLogoutClick.invoke()
    }
}