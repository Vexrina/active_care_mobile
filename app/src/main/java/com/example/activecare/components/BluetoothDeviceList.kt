package com.example.activecare.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.screens.person.domain.BluetoothDeviceDomain
import com.example.activecare.ui.theme.AppTheme

@Composable
fun BluetoothDeviceList(
    title: String,
    devices: List<BluetoothDeviceDomain>,
    onClick: (BluetoothDeviceDomain) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            TextComponent(
                text = title,
                modifier = Modifier,
                textSize = 24.sp,
                textColor = AppTheme.colors.LightText
            )
        }
        items(devices) { device ->
            Text(
                text = device.name ?: "(No name)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        onClick = {
                            onClick.invoke(device)
                        }
                    )
            )
        }
    }
}